package com.example.proyecto.ui.ListaEventos;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto.R;
import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.Modelo.Evento;
import com.example.proyecto.Room.Modelo.EventoMontana;
import com.example.proyecto.ui.ListaEventos.placeholder.PlaceholderItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class EventoFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private String nombre;
    private String fecha;

    public static final List<PlaceholderItem> ITEMS = new ArrayList<>();
    public ListaEventosAdapter adapter = new ListaEventosAdapter(ITEMS);
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventoFragment() {

    }

    /*Crea una nueva fila*/
    @SuppressWarnings("unused")
    public static EventoFragment newInstance(int columnCount,String nom, String fech) {
        EventoFragment fragment = new EventoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);

        fragment.setArguments(args);
        return fragment;
    }

    /* recoje el numero de filas, como argumento*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Evento> eventos = AppDatabase.getInstance(getContext()).eventoDAO().getAll();
                    List<EventoMontana> eventosMontana = AppDatabase.getInstance(getContext()).eventoMontanaDAO().getAll();
                    Log.i("Recoleccion", "eventos: "+eventos.size());
                    Log.i("Recoleccion", "eventosMontana: "+eventosMontana.size());
                    int i=0;
                    for (i=0; i<eventos.size() ;i++){
                        ITEMS.add(new PlaceholderItem(String.valueOf(i),eventos.get(i).getTitulo(),eventos.get(i).getFecha().toString(), true));
                    }

                    for (int a = i;i-a<eventosMontana.size();i++){
                        ITEMS.add(new PlaceholderItem(String.valueOf(i),eventosMontana.get(i-a).getTitulo(),eventosMontana.get(i-a).getFecha().toString(), false));
                    }
                    requireActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
                }
            }).start();


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /*Inicialializa all */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evento_lista, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(adapter);
        }
        return view;
    }
}