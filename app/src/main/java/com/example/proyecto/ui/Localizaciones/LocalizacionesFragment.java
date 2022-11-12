package com.example.proyecto.ui.Localizaciones;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto.Json.JsonSingleton;
import com.example.proyecto.Json.Municipio;
import com.example.proyecto.R;
import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.Modelo.Evento;
import com.example.proyecto.ui.ListaEventos.ListaEventosFragment;
import com.example.proyecto.ui.ListaEventos.placeholder.PlaceholderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


public class LocalizacionesFragment extends Fragment {

    private Context mContext;

    public static final List<String> ITEMS = new ArrayList<>();
    //public  adapter = new ListaEventosFragment.ListaEventosAdapter(ITEMS);


    public LocalizacionesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {

                    /*List<Evento> eventos = AppDatabase.getInstance(getContext()).eventoDAO().getAll();
                    Log.i("Recoleccion", "eventos: "+eventos.size());
                    int i=0;
                    ITEMS.clear();
                    for (ListIterator<Evento> iter = eventos.listIterator(); iter.hasNext(); i++){
                        Evento event = iter.next();
                        String[] fecha = event.getFecha().toString().split(" ");
                        ITEMS.add(new PlaceholderItem(event.getIde(), String.valueOf(i),event.getTitulo(),fecha[2]+"/"+fecha[1]+"/"+fecha[5], event.getEsMunicipio()));
                    }

                    requireActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());*/

                    // Se obtiene el mapa de los municipios
                    Map<String, Municipio> municipios = JsonSingleton.getInstance().municipioMap;
                    Log.i("Recoleccion", "municipios: " + municipios.keySet().size());

                }
            }).start();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_localizaciones, container, false);
    }
}