package com.example.proyecto.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.proyecto.Json.JsonSingleton;
import com.example.proyecto.R;
import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.DAO.EventoDAO;
import com.example.proyecto.Room.DAO.EventoMontanaDAO;
import com.example.proyecto.Room.Modelo.Evento;
import com.example.proyecto.Room.Modelo.EventoMontana;
import com.example.proyecto.Room.javadb.DateConverter;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearEvento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearEvento extends Fragment {

    private EditText nombreEvento, fechaEvento, localidadEvento, descripcionEvento;
    RadioButton esMunicipio;
    private Button botonCrear;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View pantallaCrear = inflater.inflate(R.layout.fragment_crear_evento2, container, false);
        nombreEvento = (EditText)pantallaCrear.findViewById(R.id.InputNombreEvento);
        localidadEvento = (EditText)pantallaCrear.findViewById(R.id.InputLocalidadEvento);
        fechaEvento = (EditText)pantallaCrear.findViewById(R.id.InputFechaEvento);
        descripcionEvento = (EditText)pantallaCrear.findViewById(R.id.InputDescripcionEvento);
        esMunicipio = (RadioButton)pantallaCrear.findViewById(R.id.OpcionMunicipio);

        botonCrear = (Button)pantallaCrear.findViewById(R.id.BotonCrear);

        botonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreEvento.toString();
                String ubicacion;
                Snackbar snackbar;
                String localidad = localidadEvento.toString();
                Date fecha = DateConverter.toDate(fechaEvento.getText().toString());
                String descripcion = descripcionEvento.toString();

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
                    if(esMunicipio.isChecked()) {
                        if (JsonSingleton.getInstance().buscarMunicipio(localidad) == null) {
                            textoError = "No se encuentra el municipio";
                            error = true;
                        }
                        else {
                            ubicacion = JsonSingleton.getInstance().buscarMunicipio(localidad).getCodigo();
                            Evento e = new Evento(nombre, ubicacion, descripcion, fecha);
                            EventoDAO eventoDAO = AppDatabase.getInstance(getContext()).eventoDAO();
                            eventoDAO.insertEvent(e);
                        }
                    }
                    else{
                        if (JsonSingleton.getInstance().buscarMontana(localidad) == null) {
                            textoError = "No se encuentra la montaña";
                            error = true;
                        }
                        else {
                            ubicacion = JsonSingleton.getInstance().buscarMontana(localidad).getCodigo();
                            EventoMontana em = new EventoMontana(nombre, ubicacion, descripcion, fecha);
                            EventoMontanaDAO eventoMontanaDAO = AppDatabase.getInstance(getContext()).eventoMontanaDAO();
                            eventoMontanaDAO.insertMontana(em);
                        }
                    }
                    if(error = true){
                        snackbar = Snackbar.make(view, textoError, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    else{
                        //Intent i = new Intent();
                        //inflater.inflate(R.layout.fragment_crear_evento2, container, false);
                    }
                }
            }
        });

        return pantallaCrear;
    }
}