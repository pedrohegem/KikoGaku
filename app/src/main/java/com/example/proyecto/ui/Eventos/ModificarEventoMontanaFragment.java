package com.example.proyecto.ui.Eventos;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.proyecto.AppContainer;
import com.example.proyecto.utils.AppExecutors;
import com.example.proyecto.MyApplication;
import com.example.proyecto.utils.JsonSingleton;
import com.example.proyecto.R;
import com.example.proyecto.models.Evento;
import com.example.proyecto.utils.DateConverter;

import com.example.proyecto.databinding.FragmentModificarEventoMontanaBinding;
import com.example.proyecto.ui.DatePickerFragment;
import com.example.proyecto.viewmodels.ModificarEventoViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;

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
    private int idEvento, diaEvento;


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
        botonModificar = binding.BotonModificar;

        ArrayList<String> ubicaciones = new ArrayList<String>(JsonSingleton.getInstance().montanaMap.keySet());

        ArrayAdapter ad = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,ubicaciones);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        localidadEvento.setAdapter(ad);

        idEvento = 0;
        if (getArguments() != null) {
            idEvento = getArguments().getInt("idEvento");
        }

        AppContainer appContainer = ((MyApplication) mContext.getApplicationContext()).appContainer;
        ModificarEventoViewModel mViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.modificarEventoMontanaViewModelFactory).get(ModificarEventoViewModel.class);

        mViewModel.getEventByID(idEvento).observe(getViewLifecycleOwner(), new Observer<Evento>() {
            @Override
            public void onChanged(Evento event) {
                if (event == null) {
                    Log.d("ERROR", "Fallo en el evento");
                } else {
                    Log.d("ELSE CHANGED", "MODIFICADO");
                    evento = event;
                    nombreEvento.setText(evento.getTitulo());
                    String[] fecha = evento.getFecha().toString().split(" ");
                    fechaEvento.setText(fecha[2] + "/" + fecha[1] + "/" + fecha[5]);
                    fechaEvento.setText(DateConverter.toString(evento.getFecha()));
                    localidadEvento.setSelection(ubicaciones.indexOf(evento.getUbicacion()));
                    descripcionEvento.setText(evento.getDescripcion());
                }
            }
        });


        botonModificar.setOnClickListener(new View.OnClickListener() {
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
                    Evento e = new Evento(nombre, localidad, descripcion, fecha, false);
                    e.setIde(evento.getIde());

                    AppExecutors.getInstance().diskIO().execute(() -> {
                        mViewModel.updateEvent(e);
                        Intent intent = new Intent(mContext, DetallesEventoActivity.class);
                        intent.putExtra("idEvento", idEvento);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    });
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
    public void onNothingSelected(AdapterView<?> arg0) {
        localidad = evento.getUbicacion();
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
                diaEvento = day;
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

    @Override
    public void onResume() {
        super.onResume();
        DetallesEventoActivity cea = (DetallesEventoActivity) getActivity();
        cea.setDayLight();
    }
}