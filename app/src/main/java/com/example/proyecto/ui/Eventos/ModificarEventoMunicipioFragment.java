package com.example.proyecto.ui.Eventos;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.example.proyecto.Json.JsonSingleton;
import com.example.proyecto.MainActivity;
import com.example.proyecto.R;
import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.DAO.EventoDAO;
import com.example.proyecto.Room.Modelo.Evento;
import com.example.proyecto.Room.javadb.DateConverter;

import com.example.proyecto.databinding.FragmentModificarEventoMunicipioBinding;
import com.example.proyecto.ui.DatePickerFragment;
import com.google.android.material.snackbar.Snackbar;

import java.security.cert.Certificate;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModificarEventoMunicipioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarEventoMunicipioFragment extends Fragment {

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
        localidadEvento = binding.SpinnerMunicipio;

        descripcionEvento = binding.InputDescripcionEvento;

        fechaEvento = binding.InputFechaEvento;
        fechaEvento.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.InputFechaEvento:
                        showDatePickerDialog();
                        break;
                }
            }
        });

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
                    Looper.prepare();
                    List<Evento> eventos = eventoDao.getEvent(finalIdEvento);
                    if (eventos.isEmpty() == true) {
                        Log.d("ERROR", "Fallo en el evento");
                    } else {
                        evento = eventos.get(0);
                        nombreEvento.setText(evento.getTitulo());
                        String[] fecha = evento.getFecha().toString().split(" ");
                        //Todo: cambiar como se ve la fecha en motana tambien y en detalles:
                        fechaEvento.setText(fecha[2] + "/" + fecha[1] + "/" + fecha[5]);
                        localidadEvento.setText(evento.getUbicacion());
                        descripcionEvento.setText(evento.getDescripcion());
                    }
                }
            }).start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

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
                if (nombre.isEmpty()) {
                    error = true;
                    textoError = "Debes introducir un nombre de evento";
                }

                if (localidad.isEmpty()) {
                    error = true;
                    textoError = "Debes introducir una localidad";
                }

                if(fecha.before(new Date(System.currentTimeMillis()-86400000))){
                    textoError = "Debe ser una fecha poserior";
                    error = true;
                }

                if (descripcion.isEmpty()) {
                    descripcion = "";
                }

                if (!JsonSingleton.getInstance().buscarMunicipio(localidad)) {
                    textoError = "No se encuentra el municipio";
                    error = true;
                }

                if (error == true) {
                    snackbar = Snackbar.make(view, textoError, Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    //ubicacion = JsonSingleton.getInstance().buscarMunicipio(localidad).getCodigo();
                    Evento e = new Evento(nombre, localidad, descripcion, fecha, true);
                    e.setIde(evento.getIde());
                    EventoDAO eventoDAO = AppDatabase.getInstance(getContext()).eventoDAO();
                    try {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                eventoDAO.updateEvent(e);

                                //Intent intent = new Intent(mContext, MainActivity.class);
                                Intent intent = new Intent(mContext, DetallesEventoActivity.class);
                                intent.putExtra("idEvento", idEvento);
                                intent.putExtra("ubicacionEvento", localidad);

                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mContext.startActivity(intent);
                            }
                        }).start();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

        return root;
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                Log.i("Fecha", "day: " + day);
                Log.i("Fecha", "month: " + month);
                Log.i("Fecha", "year: " + year);
                final String selectedDate = day + "/" + (month + 1) + "/" + year;
                fechaEvento.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
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