package com.example.proyecto.ui.Eventos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.example.proyecto.Json.JsonSingleton;
import com.example.proyecto.R;
import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.DAO.EventoDAO;
import com.example.proyecto.Room.Modelo.Evento;
import com.example.proyecto.Room.javadb.DateConverter;

import com.example.proyecto.databinding.FragmentModificarEventoMunicipioBinding;
import com.google.android.material.snackbar.Snackbar;

import java.security.cert.Certificate;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModificarEventoMunicipioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarEventoMunicipioFragment extends Fragment{

    private Context mContext;
    private DetallesEventoActivity main;

    private EditText nombreEvento, fechaEvento, descripcionEvento, localidadEvento;

    private Button botonModificar;

    private Evento evento;
    private int idEvento;

    private FragmentModificarEventoMunicipioBinding binding;

    public ModificarEventoMunicipioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return Una nueva instancia del fragment ModificarEventoMunicipioFragment.
     */
    //todo poner parametros para permitir destruccion de la activity
    public static ModificarEventoMunicipioFragment newInstance(Evento event) {
        ModificarEventoMunicipioFragment fragment = new ModificarEventoMunicipioFragment();
        Bundle args = new Bundle();
        //args.put;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                String result = bundle.getString("NombreEvento");
            }
        });*/

        //int idEvento = getArguments().getInt("idEvento", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentModificarEventoMunicipioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        nombreEvento = binding.InputNombreEvento;
        localidadEvento = binding.InputMunicipio;

        fechaEvento = binding.InputFechaEvento;
        descripcionEvento = binding.InputDescripcionEvento;

        botonModificar = binding.BotonModificar;

        idEvento = 0;
        if (getArguments() != null) {
            idEvento = getArguments().getInt("idEvento");
        }

        EventoDAO eventoDao = AppDatabase.getInstance(mContext).eventoDAO();
        try {
            int finalIdEvento = idEvento;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Evento> eventos = eventoDao.getEvent(finalIdEvento);
                    if(eventos.isEmpty() == true){
                        //todo gestionar error
                        Log.d("ERROR", "AAAAAAAAAAAAAAAAAAAA");
                    }
                    else{
                        evento = eventos.get(0);
                        nombreEvento.setText(evento.getTitulo());
                        fechaEvento.setText(DateConverter.toString(evento.getFecha()));
                        localidadEvento.setText(evento.getUbicacion());
                        descripcionEvento.setText(evento.getDescripcion());
                    }
                }
            }).start();
        } catch (Exception exception){
            exception.printStackTrace();
        }


        //todo buscar localidad con el JSON
        //localidadEvento.setText(evento.getUbicacionCode());

        /*nombreEvento.setText(getArguments().getString("nombreEvento", "Nombre del evento"));

        //localidadEvento.set(savedInstanceState.getString("localidadEvento"));
        fechaEvento.setText(getArguments().getString("fechaEvento", "01/01/2000"));
        descripcionEvento.setText(getArguments().getString("descripcionEvento", ""));*/

        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreEvento.getText().toString();
                Snackbar snackbar;

                Date fecha = DateConverter.toDate(fechaEvento.getText().toString());
                String descripcion = descripcionEvento.getText().toString();
                String localidad = localidadEvento.getText().toString();

                String textoError = "";
                boolean error = false;
                if(nombre == null) {
                    error = true;
                    textoError = "Debes introducir un nombre de evento";
                }
                if(localidad == null){
                    error = true;
                    textoError = "Debes introducir una localidad";
                }
                if(descripcion == null){
                    descripcion = "Sin descripción";
                }

                if(error == true) {
                    snackbar = Snackbar.make(view, textoError, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else{
                    if(evento.getEsMunicipio()) {
                        if (JsonSingleton.getInstance().buscarMunicipio(localidad)) {
                            textoError = "No se encuentra el municipio";
                            error = true;
                        }
                        else {
                            //ubicacion = JsonSingleton.getInstance().buscarMunicipio(localidad).getCodigo();
                            Evento e = new Evento(nombre, localidad, descripcion, fecha, true);
                            e.setIde(evento.getIde());
                            EventoDAO eventoDAO = AppDatabase.getInstance(getContext()).eventoDAO();
                            try {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        eventoDAO.updateEvent(e);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("idEvento",idEvento);
                                        requireActivity().runOnUiThread(() -> NavHostFragment.findNavController(ModificarEventoMunicipioFragment.this).navigate(R.id.action_nav_modificar_evento_municipio_to_nav_detalles_evento,bundle));
                                    }
                                }).start();
                            } catch (Exception exception){
                                exception.printStackTrace();
                            }
                        }
                    }
                    else{
                        if (JsonSingleton.getInstance().buscarMontana(localidad) == null) {
                            textoError = "No se encuentra la montaña";
                            error = true;
                        }
                        else {
                            //ubicacion = JsonSingleton.getInstance().buscarMontana(localidad).getCodigo();
                            Evento e = new Evento(nombre, localidad, descripcion, fecha, false);
                            e.setIde(evento.getIde());
                            EventoDAO eventoDAO = AppDatabase.getInstance(getContext()).eventoDAO();
                            try {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        eventoDAO.updateEvent(e);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("idEvento",idEvento);
                                        Fragment detalles= new DetallesEventoFragment();
                                        detalles.setArguments(bundle);
                                        getActivity().getSupportFragmentManager().popBackStack();
                                    }
                                }).start();
                            } catch (Exception exception){
                                exception.printStackTrace();
                            }
                        }
                    }
                    if(error = true){
                        snackbar = Snackbar.make(view, textoError, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    else{
                        //Intent i = new Intent();
                        inflater.inflate(R.layout.fragment_crear_evento_municipio, container, false);
                    }
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        main = (DetallesEventoActivity) main;
        mContext = context;
    }
}