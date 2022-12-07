package com.example.proyecto.ui.ListaEventos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyecto.AppContainer;
import com.example.proyecto.MainActivity;
import com.example.proyecto.MyApplication;
import com.example.proyecto.R;
import com.example.proyecto.repository.EventRepository;
import com.example.proyecto.repository.room.AppDatabase;
import com.example.proyecto.models.Evento;
import com.example.proyecto.databinding.EventoListaBinding;
import com.example.proyecto.ui.Eventos.DetallesEventoActivity;
import com.example.proyecto.ui.ListaEventos.placeholder.PlaceholderItem;
import com.example.proyecto.viewmodels.ListaEventosViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A fragment representing a list of Items.
 */
public class ListaEventosFragment extends Fragment {
    private static final String TAG = "ListaEventosFragment";

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_TIPO_FILTRO = "PosicionTab";
    private static final String ARG_FILTRO_ORDEN = "PosicionSpinner";

    private EventRepository eventRepository;
    private Context mContext;
    private int mColumnCount = 1;
    private int filtroRealizado = -1;
    private int filtroOrden = -1;

    public static final List<PlaceholderItem> ITEMS = new ArrayList<>();
    public ListaEventosAdapter adapter = new ListaEventosAdapter(ITEMS);
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListaEventosFragment() {

    }


    /* recoje el numero de filas, como argumento*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Creando lista...");

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            filtroRealizado = getArguments().getInt(ARG_TIPO_FILTRO, -1);
            filtroOrden = getArguments().getInt(ARG_FILTRO_ORDEN, -1);
        }

        adapter.mValues.clear(); // clear list
        ITEMS.clear();
        adapter.notifyDataSetChanged();

        AppContainer appContainer = ((MyApplication) mContext.getApplicationContext()).appContainer;
        ListaEventosViewModel mViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.listaEventosViewModelFactory).get(ListaEventosViewModel.class);

        mViewModel.getEventos().observe(this, new Observer<List<Evento>>() {
            @Override
            public void onChanged(List<Evento> eventos) {
                updateUI(eventos);
            }
        });
    }


    public void updateUI( List<Evento> listaEventos ) {
        Log.d(TAG, "List size: " + listaEventos.size());
        List<PlaceholderItem> placeholderItems = new ArrayList<>();

        //  Filtrar la Lista según el tipo de filtrado
        List<Evento> filteredList = filterList(listaEventos);

        for (ListIterator<Evento> iter = filteredList.listIterator(); iter.hasNext();){
            Evento event = iter.next();
            String[] fecha = event.getFecha().toString().split(" ");
            ITEMS.add(new PlaceholderItem(event.getIde(),event.getTitulo(),fecha[2]+"/"+fecha[1]+"/"+fecha[5], event.getUbicacion(), event.getEsMunicipio()));
            placeholderItems.add(new PlaceholderItem(event.getIde(),event.getTitulo(),fecha[2]+"/"+fecha[1]+"/"+fecha[5], event.getUbicacion(), event.getEsMunicipio()));
        }
        adapter.mValues = placeholderItems;
        requireActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
    }


    public List<Evento> filterList (List<Evento> listToFilter) {
        List<Evento> filteredList = new ArrayList<>();

        Predicate<Evento> byMunicipio = evento -> evento.getEsMunicipio();
        Predicate<Evento> byMontaña = evento -> !evento.getEsMunicipio();

        switch (filtroRealizado){
            case -1: // Sin filtro
                filteredList = listToFilter;
                break;
            case 0: // Eventos de Municipio
                filteredList = listToFilter.stream().filter(byMunicipio).collect(Collectors.toList());
                break;
            case 1:
                filteredList = listToFilter.stream().filter(byMontaña).collect(Collectors.toList());
                break;
        }

        if (filtroOrden == 1){ // Ordenado por fecha
            Collections.sort(filteredList);
        }

        return filteredList;
    }

    /*Inicialializa el recyclerview */
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public class ListaEventosAdapter extends RecyclerView.Adapter<ListaEventosAdapter.ViewHolder> {

        private List<PlaceholderItem> mValues;

        public ListaEventosAdapter(List<PlaceholderItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(EventoListaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetallesEventoActivity.class);
                    intent.putExtra("idEvento",holder.mItem.ide);
                    intent.putExtra("ubicacionEvento", holder.mItem.localizacion);
                    String[] fechaEvento = holder.mItem.fecha.split("/");
                    Calendar cal = Calendar.getInstance();
                    int diaActual = cal.get(Calendar.DAY_OF_MONTH);

                    if(diaActual == Integer.parseInt(fechaEvento[0])) { // Si el evento es en el día actual....
                        intent.putExtra("diaEvento", -1);
                    } else {
                        intent.putExtra("diaEvento", Integer.parseInt(fechaEvento[0]) - diaActual);
                    }

                    intent.putExtra("esMunicipio", holder.mItem.evento);
                    mContext.startActivity(intent);
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mNombreView.setText(mValues.get(position).nombre);
            holder.mFechaView.setText(mValues.get(position).fecha);
            if(mValues.get(position).evento){
                holder.mIcono.setImageResource(R.drawable.ic_evento);
            }else{
                holder.mIcono.setImageResource(R.drawable.ic_evemontanas);
            }
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        /* Representa a la fila que se muestra */
        public class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView mNombreView;
            public final TextView mFechaView;
            public final ImageView mIcono;
            public PlaceholderItem mItem;

            public ViewHolder(EventoListaBinding binding) {
                super(binding.getRoot());
                mNombreView = binding.Nombre;
                mFechaView = binding.Fecha;
                mIcono = binding.iconoEvento;
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mNombreView.getText() + "'";
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity cea = (MainActivity) getActivity();
        cea.setDayLight();
    }
}