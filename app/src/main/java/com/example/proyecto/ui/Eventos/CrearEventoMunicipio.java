package com.example.proyecto.ui.Eventos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.proyecto.utils.AppExecutors;
import com.example.proyecto.repository.EventRepository;
import com.example.proyecto.utils.JsonSingleton;
import com.example.proyecto.R;
import com.example.proyecto.repository.room.AppDatabase;
import com.example.proyecto.models.Evento;
import com.example.proyecto.utils.DateConverter;
import com.example.proyecto.databinding.FragmentCrearEventoMunicipioBinding;
import com.example.proyecto.ui.DatePickerFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

public class CrearEventoMunicipio extends Fragment{

    private Context mContext;

    private CrearEventoActivity main;

    private EditText nombreEvento, fechaEvento, descripcionEvento, localidadEvento;
    private Button botonCrear;

    private Evento e;
    int idEvento, diaEvento;

    FragmentCrearEventoMunicipioBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCrearEventoMunicipioBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        nombreEvento = binding.InputNombreEvento;
        localidadEvento = binding.inputLocalidad;

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
                String localidad = localidadEvento.getText().toString();
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

                if (!JsonSingleton.getInstance().buscarMunicipio(localidad)) {
                    textoError = "No se encuentra el municipio";
                    error = true;
                }

                if(fecha.before(new Date(System.currentTimeMillis()-86400000))){
                    textoError = "Debe ser una fecha poserior";
                    error = true;
                }

                if (error == true) {
                    snackbar = Snackbar.make(view, textoError, Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    // Ya tenemos los datos del formulario
                    e = new Evento(nombre, localidad, descripcion, fecha, true);
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        idEvento = EventRepository.getInstance(AppDatabase.getInstance(mContext).eventoDAO()).insertEvent(e);

                        Intent intent = new Intent(mContext, DetallesEventoActivity.class);
                        intent.putExtra("idEvento", idEvento);
                        intent.putExtra("esMunicipio", true);

                        startActivity(intent);
                    });

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
                diaEvento = day;
                fechaEvento.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
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
}