package com.example.proyecto.ui.Eventos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proyecto.MainActivity;
import com.example.proyecto.R;
import com.example.proyecto.databinding.FragmentCrearEventoBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearEvento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearEvento extends Fragment{

    private Context mContext;
    private CrearEventoActivity main;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrearEventoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button BotonCrearMunicipio = view.findViewById(R.id.CrearMunicipio);
        Button BotonCrearMontana = view.findViewById(R.id.CrearMontana);

        BotonCrearMunicipio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    NavHostFragment.findNavController(CrearEvento.this).navigate(R.id.action_crearEvento_to_crearEventoMunicipio2);
            }
        });

        BotonCrearMontana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    NavHostFragment.findNavController(CrearEvento.this).navigate(R.id.action_crearEvento_to_crearEventoMontana2);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        main = (CrearEventoActivity) context;
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
