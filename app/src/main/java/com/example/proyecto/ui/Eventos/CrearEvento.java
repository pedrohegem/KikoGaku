package com.example.proyecto.ui.Eventos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

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
    private MainActivity main;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*binding = FragmentCrearEventoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button BotonCrearMunicipio = binding.CrearMunicipio;
        Button BotonCrearMontana = binding.CrearMontana;


        BotonCrearMunicipio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrearEventoMunicipio fragmentoCrearMunicipio = new CrearEventoMunicipio();
                FragmentManager fragmentManager = main.getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, fragmentoCrearMunicipio).commit();
            }
        });

        BotonCrearMontana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrearEventoMunicipio fragmentoCrearMunicipio = new CrearEventoMunicipio();
                FragmentManager fragmentManager = main.getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, fragmentoCrearMunicipio).commit();
            }
        });
        return root*/
        return inflater.inflate(R.layout.fragment_crear_evento, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button BotonCrearMunicipio = view.findViewById(R.id.CrearMunicipio);
        Button BotonCrearMontana = view.findViewById(R.id.CrearMontana);

        BotonCrearMunicipio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*CrearEventoMunicipio fragmentoCrearMunicipio = new CrearEventoMunicipio();
                FragmentManager fragmentManager = main.getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, fragmentoCrearMunicipio).commit();
                */
                Navigation.findNavController(v).navigate(R.id.nav_crear_municipio);
            }
        });

        BotonCrearMontana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearEventoMontana fragmentoCrearMontana = new CrearEventoMontana();
                FragmentManager fragmentManager = main.getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, fragmentoCrearMontana).commit();
            }
        });
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
