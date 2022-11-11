package com.example.proyecto.ui.Eventos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.proyecto.Json.JsonSingleton;
import com.example.proyecto.MainActivity;
import com.example.proyecto.R;
import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.DAO.EventoDAO;
import com.example.proyecto.Room.Modelo.Evento;
import com.example.proyecto.Room.javadb.DateConverter;

import com.example.proyecto.databinding.FragmentModificarEventoMontanaBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModificarEventoMontanaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarEventoMontanaFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Context mContext;
    private DetallesEventoActivity main;

    private EditText nombreEvento, fechaEvento, descripcionEvento;
    Spinner localidadEvento;
    private Button botonModificar;
    private Evento evento;
    private String localidad;
    private int idEvento;


    private FragmentModificarEventoMontanaBinding binding;

    public ModificarEventoMontanaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return Una nueva instancia del fragment ModificarEventoMunicipioFragment.
     */
    //todo poner parametros para permitir destruccion de la activity
    public static ModificarEventoMontanaFragment newInstance(Evento event) {
        ModificarEventoMontanaFragment fragment = new ModificarEventoMontanaFragment();
        Bundle args = new Bundle();
        //args.put;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentModificarEventoMontanaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        nombreEvento = binding.InputNombreEvento;
        localidadEvento = binding.SpinnerMunicipio;
        localidadEvento.setOnItemSelectedListener(this);
        fechaEvento = binding.InputFechaEvento;
        descripcionEvento = binding.InputDescripcionEvento;
        botonModificar = binding.BotonModificar;

        ArrayList<String> ubicaciones = new ArrayList<String>(JsonSingleton.getInstance().montanaMap.keySet());

        ArrayAdapter ad = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,ubicaciones);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        localidadEvento.setAdapter(ad);

        idEvento = 0;
        if (getArguments() != null) {
            idEvento = getArguments().getInt("idEvento");
        }

        EventoDAO eventoDao = AppDatabase.getInstance(mContext).eventoDAO();
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Evento> eventos = eventoDao.getEvent(idEvento);
                    if(eventos.isEmpty() == true){
                        //todo gestionar error
                        Log.d("ERROR", "Fallo en el evento");
                    }
                    else{
                        evento = eventos.get(0);
                        nombreEvento.setText(evento.getTitulo());
                        fechaEvento.setText(DateConverter.toString(evento.getFecha()));
                        localidadEvento.setSelection(ubicaciones.indexOf(evento.getUbicacion()));
                        descripcionEvento.setText(evento.getDescripcion());
                    }
                }
            }).start();
        } catch (Exception exception){
            exception.printStackTrace();
        }
        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreEvento.getText().toString();
                Snackbar snackbar;

                Date fecha = DateConverter.toDate(fechaEvento.getText().toString());
                String descripcion = descripcionEvento.getText().toString();

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
                            Evento e = new Evento(nombre, localidad, descripcion, fecha, true);
                            e.setIde(evento.getIde());
                            EventoDAO eventoDAO = AppDatabase.getInstance(getContext()).eventoDAO();
                            try {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        eventoDAO.updateEvent(e);
                                        Intent intent = new Intent(mContext, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        mContext.startActivity(intent);
                                    }
                                }).start();
                            } catch (Exception exception){
                                exception.printStackTrace();
                            }
                        }
                    }
                    else{
                        Evento e = new Evento(nombre, localidad, descripcion, fecha, false);
                        e.setIde(evento.getIde());
                        EventoDAO eventoDAO = AppDatabase.getInstance(getContext()).eventoDAO();
                        try {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    eventoDAO.updateEvent(e);
                                }
                            }).start();
                        } catch (Exception exception){
                            exception.printStackTrace();
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
    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
        ArrayList<String> ubicaciones = new ArrayList<String>(JsonSingleton.getInstance().montanaMap.keySet());
        localidad = ubicaciones.get(pos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0)
    {
        localidad = evento.getUbicacion();
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