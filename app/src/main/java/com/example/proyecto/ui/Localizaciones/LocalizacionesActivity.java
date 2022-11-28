package com.example.proyecto.ui.Localizaciones;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.utils.JsonSingleton;
import com.example.proyecto.models.Municipio;
import com.example.proyecto.R;
import com.example.proyecto.databinding.ActivityLocalizacionesBinding;
import com.example.proyecto.databinding.LocalizacionListaBinding;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocalizacionesActivity extends AppCompatActivity {

    private ActivityLocalizacionesBinding binding;
    private Context mContext;

    private List<String> ITEMS = new ArrayList<>();
    private LocalizacionesAdapter adapter = new LocalizacionesAdapter(ITEMS);
    private RecyclerView recyclerView;


    public LocalizacionesActivity() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Se establece el contexto
        mContext = getApplicationContext();

        // Se establece la toolbar

        binding = ActivityLocalizacionesBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Localizaciones");

        setContentView(binding.getRoot());

        // Se leen los municipios y se cargan en el arraylist

        Map<String, Municipio> municipios = JsonSingleton.getInstance().municipioMap;
        Log.i("Recoleccion", "municipios: " + municipios.keySet().size());
        ITEMS.clear();

        for (String name : municipios.keySet()){
            System.out.println("key: " + name);
            ITEMS.add(name);
        }
        adapter.notifyDataSetChanged();

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        // Se obtiene e implementa el SearchView

        SearchView searchView = binding.searchView;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Al cambiar el texto del SearchView, se llama al filtro
                filter(newText);
                return false;
            }
        });

    }


    // Método llamado en el listener del SearchView, se encarga de filtrar los View de RecyclerView
    private void filter(String text) {
        ArrayList<String> filteredlist = new ArrayList<String>(); // Nuevo arrayList para filtrar los datos

        if(text.length() == 0){ // Si el texto de busqueda está vacio, se llena el vector filtrado de todos los municipios
            Log.d("CERO", "SIN");
            filteredlist.clear();
            filteredlist.addAll(ITEMS);
        }
        else{ // Si la cadena coincide con varios municipios
            ArrayList<String> keyset = new ArrayList<String>(JsonSingleton.getInstance().municipioMap.keySet());
            
            filteredlist = (ArrayList<String>) findWithPrefix(keyset, text);
        }

        adapter.filterList(filteredlist);

    }


    // Método que se encarga de devolver la lista de municipios que contengan un prefijo del SearchView
    public static List<String> findWithPrefix(List<String> list, String prefix) {
        List<String> result = new ArrayList<>();
        for(String s : list)

            if(s.toLowerCase().startsWith(prefix.toLowerCase()))
                result.add(s);
        return result;
    }

    
    /* Clase intermediaria entre los datos y la vista */

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
                    Intent intent = new Intent(mContext, DetalleLocalizacionActivity.class);
                    intent.putExtra("ubicacion", holder.mItem);
                    startActivity(intent);

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


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    // Método que se encarga de eliminar las tildes de un String. No se utiliza
    public static String unaccent(String src) {
        return Normalizer
                .normalize(src, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    public void setDayLight(){
        // Para obtener la configuracion que el usuario ha introducido previamente en la app, se obtiene el objeto SharedPreferences
        SharedPreferences sp = getSharedPreferences("preferences", this.MODE_PRIVATE);
        int tema = sp.getInt("Theme", 1);
        Log.d("NUMERO MODO", String.valueOf(tema));
        if(tema == 0){ // Modo claro
            Log.d("DENTRO CLARO", "AAAAAAAAAAAAAAAAAAAAAAA");
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO); // método que da error
        }
        else{ // Modo oscuro
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se llama al comienzo de la actividad al setDayLight() para saber si el modo claro está activado
        setDayLight();
    }
}