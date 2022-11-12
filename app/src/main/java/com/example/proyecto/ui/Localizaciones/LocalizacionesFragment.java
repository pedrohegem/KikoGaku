package com.example.proyecto.ui.Localizaciones;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto.Json.JsonSingleton;
import com.example.proyecto.Json.Municipio;
import com.example.proyecto.R;
import com.example.proyecto.databinding.FragmentLocalizacionesBinding;
import com.example.proyecto.databinding.LocalizacionListaBinding;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class LocalizacionesFragment extends Fragment {

    private Context mContext;

    public static final List<String> ITEMS = new ArrayList<>();
    public LocalizacionesAdapter adapter = new LocalizacionesAdapter(ITEMS);

    private FragmentLocalizacionesBinding binding;

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
                    // Se obtiene el mapa de los municipios
                    Map<String, Municipio> municipios = JsonSingleton.getInstance().municipioMap;
                    Log.i("Recoleccion", "municipios: " + municipios.keySet().size());
                    ITEMS.clear();

                    for (String name : municipios.keySet()){
                        System.out.println("key: " + name);
                        ITEMS.add(name);
                    }
                    requireActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
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
        View view = inflater.inflate(R.layout.fragment_localizaciones, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
        }

        // Se obtiene e implementa el SearchView
        //binding = FragmentLocalizacionesBinding.inflate(inflater, container, false);
        //SearchView searchView = binding.searchView;

        // Se implementa el listener del searchview
        /*
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                //filter(newText);
                return false;
            }
        });
*/
        return view;
    }

    // Método llamado en el listener del SearchView, se encarga de filtrar los View de RecyclerView
    private void filter(String text) {
        // Nuevo arrayList para filtrar los datos
        ArrayList<String> filteredlist = new ArrayList<String>();

        // Se comprueba si la cadena introducida existe en el mapa de municipios
        if(JsonSingleton.getInstance().municipioMap.containsKey(text)){
            filteredlist.add(text);
        }

        if (filteredlist.isEmpty()) {
            // Si no existe ninguna coincidencia
            Toast.makeText(mContext, "No existe ninguna coincidencia", Toast.LENGTH_SHORT).show();
        } else {
            // Se pasa el listado de coincidencias al adapter
            adapter.filterList(filteredlist);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public class LocalizacionesAdapter extends RecyclerView.Adapter<LocalizacionesAdapter.ViewHolder> {

        private List<String> mValues;

        public LocalizacionesAdapter(List<String> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(LocalizacionListaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    // Redirige a detalles de una localización
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        // Método encargado de actualizar la lista de municipios segun el nuevo arraylist filtrado
        public void filterList(ArrayList<String> filterlist) {
            // Actualiza el listado de vistas
            mValues = filterlist;
            notifyDataSetChanged();
        }

        /* Clase que representa a la fila que se muestra */

        public class ViewHolder extends RecyclerView.ViewHolder {
            // Cada fila tiene el nombre de localidad y el icono del tiempo
            public final TextView mIdView;
            public String mItem;
            // public final ImageView mIcono;

            public ViewHolder(LocalizacionListaBinding binding) {
                super(binding.getRoot());
                mIdView = binding.NombreLocalizacion;
            }


            @Override
            public String toString() {
                return super.toString() + " '" + mIdView.getText() + "'";
            }
        }
    }
}