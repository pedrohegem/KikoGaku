package com.example.proyecto.ui.Eventos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.example.proyecto.R;
import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.DAO.EventoDAO;
import com.example.proyecto.Room.Modelo.Evento;
import com.example.proyecto.Room.javadb.DateConverter;
import com.example.proyecto.databinding.FragmentCrearEventoBinding;
import com.example.proyecto.databinding.FragmentCrearEventoMontanaBinding;
import com.example.proyecto.databinding.FragmentCrearEventoMunicipioBinding;
import com.example.proyecto.ui.DatePickerFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearEvento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearEvento extends Fragment{

    private Context mContext;

    private EditText nombreEvento, fechaEvento, descripcionEvento;
    private Spinner localidadEvento;
    private Button botonCrear;

    String localidad;

    private FragmentCrearEventoBinding binding;

    public CrearEvento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return Una nueva instancia del fragment CrearEvento.
     */
    //todo poner parametros para permitir destruccion de la activity
    public static CrearEvento newInstance() {
        CrearEvento fragment = new CrearEvento();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCrearEventoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button BotonCrearMunicipio = binding.CrearMunicipio;
        Button BotonCrearMontana = binding.CrearMontana;

        BotonCrearMunicipio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentCrearEventoMunicipioBinding pantallaMunicipio = FragmentCrearEventoMunicipioBinding.inflate(inflater, container, false);

                nombreEvento = pantallaMunicipio.InputNombreEvento;
                localidadEvento = pantallaMunicipio.Spinner;
                localidadEvento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
                        localidad = parent.getItemAtPosition(pos).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });

                ArrayList<String> ubicaciones = new ArrayList<String>();

                //todo obtener en ubicaciones todos los nombres de municipios

                /* ubicaciones.addAll();*/
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ubicaciones);

                /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.planets_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
                localidadEvento.setAdapter(adapter);

                fechaEvento = pantallaMunicipio.InputFechaEvento;

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

                descripcionEvento = pantallaMunicipio.InputDescripcionEvento;
                botonCrear = pantallaMunicipio.BotonModificar;
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

                            Evento e = new Evento(nombre, localidad, descripcion, fecha, true);
                            EventoDAO eventoDAO = AppDatabase.getInstance(getContext()).eventoDAO();
                            try {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        eventoDAO.insertEvent(e);
                                    }
                                }).start();
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

        BotonCrearMontana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentCrearEventoMontanaBinding pantallaMontana = FragmentCrearEventoMontanaBinding.inflate(inflater, container, false);

                nombreEvento = pantallaMontana.InputNombreEvento;
                localidadEvento = pantallaMontana.Spinner;
                localidadEvento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
                        localidad = parent.getItemAtPosition(pos).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });

                ArrayList<String> ubicaciones = new ArrayList<String>();

                //todo obtener en ubicaciones todos los nombres de montañas

                /* ubicaciones.addAll();*/
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ubicaciones);

                /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.planets_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
                localidadEvento.setAdapter(adapter);

                fechaEvento = pantallaMontana.InputFechaEvento;

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

                descripcionEvento = pantallaMontana.InputDescripcionEvento;
                botonCrear = pantallaMontana.BotonModificar;
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
                            ubicacion = JsonSingleton.getInstance().buscarMontana(localidad).getCodigo();
                            Evento e = new Evento(nombre, localidad, descripcion, fecha, false);
                            EventoDAO eventoDAO = AppDatabase.getInstance(getContext()).eventoDAO();
                            try {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        eventoDAO.insertEvent(e);
                                    }
                                }).start();
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    /*new AsyncTask<Void, Void, String>(){
         @Override
         protected String doInBackground(Void... voids){
             return null;
         }

         @Override
         protected void onPostExecute(String result){

         }
    }.execute();*/
                }
/*EventoDAO eventoDAO = AppDatabase.getInstance(getContext()).eventoDAO();
new Thread(new Runnable() {
@Override
public void run() {
        for (Evento e : eventoDAO.getAll()) {
        Log.d("AVISO:", e.getTitulo() + "-" + e.getDescripcion());
        }
        }
        }).start();*/
