package com.example.proyecto.ui.Eventos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.proyecto.Json.JsonSingleton;
import com.example.proyecto.MainActivity;
import com.example.proyecto.R;
import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.DAO.EventoDAO;
import com.example.proyecto.Room.Modelo.Evento;
import com.example.proyecto.Room.javadb.DateConverter;
import com.example.proyecto.databinding.FragmentCrearEventoMunicipioBinding;
import com.example.proyecto.ui.DatePickerFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearEventoMunicipio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearEventoMunicipio extends Fragment implements AdapterView.OnItemSelectedListener{

    private MainActivity main;

    private EditText nombreEvento, fechaEvento, descripcionEvento;
    private Spinner localidadEvento;
    private Button botonCrear;

    private Evento evento;

    String localidad;

    // TODO: Rename and change types of parameters
    private String nombreM, localidadM, fechaM, descripcionM;

    FragmentCrearEventoMunicipioBinding binding;

    public CrearEventoMunicipio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CrearEventoMunicipio.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearEventoMunicipio newInstance(String NombreEvento, String DescripcionEvento) {
        CrearEventoMunicipio fragment = new CrearEventoMunicipio();
        Bundle args = new Bundle();
        args.putString("NombreEvento", NombreEvento);
        args.putString("DescripcionEvento", DescripcionEvento);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nombreM = getArguments().getString("NombreEvento");
            descripcionM = getArguments().getString("DescripcionEvento");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCrearEventoMunicipioBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        nombreEvento = binding.InputNombreEvento;
        localidadEvento = binding.Spinner;
        localidadEvento.setOnItemSelectedListener(this);

        ArrayList<String> ubicaciones = new ArrayList<String>(JsonSingleton.getInstance().municipioMap.keySet());
        String[] listaUbicaciones =  new String[ubicaciones.size()];
        listaUbicaciones = ubicaciones.toArray(listaUbicaciones);

        ArrayAdapter ad = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, listaUbicaciones);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        localidadEvento.setAdapter(ad);

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

        descripcionEvento = binding.InputDescripcionEvento;
        botonCrear = binding.BotonModificar;
        botonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreEvento.getText().toString();
                String ubicacion;
                Snackbar snackbar;

                //todo obtener la ubicacion
                String localidad = localidadEvento.toString();
                Date fecha = DateConverter.toDate(fechaEvento.getText().toString());
                String descripcion = descripcionEvento.getText().toString();

                String textoError = "";
                boolean error = false;
                if (nombre == null) {
                    error = true;
                    textoError = "Debes introducir un nombre de evento";
                }
                if (localidad == null) {
                    error = true;
                    textoError = "Debes introducir una localidad";
                }
                if (descripcion == null) {
                    descripcion = "Sin descripción";
                }

                //todo hacer que las localidades provengan del json
                if (JsonSingleton.getInstance().buscarMunicipio(localidad) == null) {
                    textoError = "No se encuentra el municipio";
                    error = true;
                }

                if (error == true) {
                    snackbar = Snackbar.make(view, textoError, Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    ubicacion = JsonSingleton.getInstance().buscarMunicipio(localidad).getCodigo();

                    evento = new Evento(nombre, localidad, descripcion, fecha, true);
                    EventoDAO eventoDAO = AppDatabase.getInstance(getContext()).eventoDAO();
                    try {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                eventoDAO.insertEvent(evento);
                                evento = eventoDAO.getEvent(evento.getTitulo()).get(0);
                            }
                        }).start();

                        DetallesEvento detallesEvento = new DetallesEvento();
                        Bundle bundle = new Bundle();
                        bundle.putInt("idEvento", evento.getIde());
                        detallesEvento.setArguments(bundle);

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, detallesEvento).commit();

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
                final String selectedDate = day + "/" + (month + 1) + "/" + year;
                fechaEvento.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
        localidad = parent.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        main = (MainActivity) context;
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}