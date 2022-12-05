package com.example.proyecto.ui.Eventos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import com.example.proyecto.models.Montana;
import com.example.proyecto.models.Weather;
import com.example.proyecto.repository.networking.APIManager;
import com.example.proyecto.repository.networking.APIManagerDelegate;
import com.example.proyecto.utils.JsonSingleton;
import com.example.proyecto.R;
import com.example.proyecto.repository.room.AppDatabase;
import com.example.proyecto.repository.room.DAO.EventoDAO;
import com.example.proyecto.models.Evento;
import com.example.proyecto.utils.DateConverter;
import com.example.proyecto.databinding.FragmentCrearEventoMontanaBinding;
import com.example.proyecto.ui.DatePickerFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CrearEventoMontana extends Fragment implements AdapterView.OnItemSelectedListener, APIManagerDelegate {
    private CrearEventoActivity main;

    private EditText nombreEvento, fechaEvento, descripcionEvento;
    private Spinner localidadEvento;
    private Button botonCrear;
    private Context mContext;
    private Evento evento;
    int idEvento;
    int diaEvento;
    String localidad;
    private APIManager api;
    private Evento e;

    private String nombreM, localidadM, fechaM, descripcionM;

    FragmentCrearEventoMontanaBinding binding;

    public CrearEventoMontana() {
    }

    public static CrearEventoMontana newInstance(String NombreEvento, String DescripcionEvento) {
        CrearEventoMontana fragment = new CrearEventoMontana();
        Bundle args = new Bundle();
        args.putString("NombreEvento", NombreEvento);
        args.putString("DescripcionEvento", DescripcionEvento);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = new APIManager(this);
        if (getArguments() != null) {
            nombreM = getArguments().getString("NombreEvento");
            descripcionM = getArguments().getString("DescripcionEvento");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCrearEventoMontanaBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        nombreEvento = binding.InputNombreEvento;
        localidadEvento = binding.SpinnerMunicipio;
        localidadEvento.setOnItemSelectedListener(this);

        ArrayList<String> ubicaciones = new ArrayList<String>(JsonSingleton.getInstance().montanaMap.keySet());

        ArrayAdapter ad = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,ubicaciones);

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
                Snackbar snackbar;

                Date fecha = DateConverter.toDate(fechaEvento.getText().toString());
                String descripcion = descripcionEvento.getText().toString();

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

                if (descripcion.isEmpty()) {
                    descripcion = "";
                }

                if(fecha.before(new Date(System.currentTimeMillis()-86400000))){
                    textoError = "Debe ser una fecha poserior";
                    error = true;
                }

                if (error == true) {
                    snackbar = Snackbar.make(view, textoError, Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    e = new Evento(nombre, localidad, descripcion, fecha, false);
                    Montana m = JsonSingleton.getInstance().montanaMap.get(localidad);
                    api.getEventWeather(m.getLatitud(),m.getLongitud());
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
                Log.i("Fecha", "day: "+day);
                Log.i("Fecha", "month: "+month);
                Log.i("Fecha", "year: "+year);
                final String selectedDate = day + "/" + (month + 1) + "/" + year;
                diaEvento = day;
                fechaEvento.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
        ArrayList<String> ubicaciones = new ArrayList<String>(JsonSingleton.getInstance().montanaMap.keySet());
        localidad = ubicaciones.get(pos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        main = (CrearEventoActivity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        CrearEventoActivity cea = (CrearEventoActivity) getActivity();
        cea.setDayLight();
    }

    @Override
    public void onGetWeatherSuccess(Weather weather) {
        e.setWeather(weather);

        EventoDAO eventoDAO = AppDatabase.getInstance(getContext()).eventoDAO();
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    idEvento = (int)eventoDAO.insertEvent(e);
                    Calendar cal = Calendar.getInstance();
                    int diaActual = cal.get(Calendar.DAY_OF_MONTH);

                    Intent intent = new Intent(mContext, DetallesEventoActivity.class);
                    intent.putExtra("idEvento", idEvento);
                    intent.putExtra("ubicacionEvento", e.getUbicacion());
                    intent.putExtra("esMunicipio", false);
                    if(diaActual == diaEvento) { // Si el evento es en el día actual....
                        intent.putExtra("diaEvento", -1);
                    } else {
                        intent.putExtra("diaEvento", diaEvento - diaActual);
                    }
                    startActivity(intent);
                }
            }).start();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onGetWeatherFailure() {

    }
}