package com.example.proyecto.ui.ListaEventos;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyecto.R;
import com.example.proyecto.databinding.EventoListaBinding;
import com.example.proyecto.ui.ListaEventos.placeholder.PlaceholderItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 *
 */
public class ListaEventosAdapter extends RecyclerView.Adapter<ListaEventosAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public ListaEventosAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(EventoListaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
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
        public final TextView mIdView;
        public final TextView mNombreView;
        public final TextView mFechaView;
        public final ImageView mIcono;
        public PlaceholderItem mItem;

        public ViewHolder(EventoListaBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
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