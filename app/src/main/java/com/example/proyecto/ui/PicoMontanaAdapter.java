package com.example.proyecto.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.Room.Modelo.PicoMontana;

import java.util.ArrayList;
import java.util.List;

public class PicoMontanaAdapter extends RecyclerView.Adapter<PicoMontanaAdapter.ViewHolder> {
    private final List<PicoMontana> mItems = new ArrayList<PicoMontana>();

    public interface OnItemClickListener {
        void onItemClick(PicoMontana picoMontana);     //Type of the element to be returned
    }

    private final OnItemClickListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public PicoMontanaAdapter(OnItemClickListener listener) {

        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        //TODO - Inflate the View for every element
        View v = null;

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position),listener);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(PicoMontana item) {

        mItems.add(item);
        notifyDataSetChanged();

    }

    public void clear(){

        mItems.clear();
        notifyDataSetChanged();

    }

    public Object getItem(int pos) {

        return mItems.get(pos);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private CheckBox statusView;
        private TextView priorityView;
        private TextView dateView;

        public ViewHolder(View itemView) {
            super(itemView);

            //TODO - Get the references to every widget of the Item View

        }

        public void bind(final PicoMontana picoMontana, final OnItemClickListener listener) {

            title.setText(picoMontana.getTempMax());

            //TODO - Display Priority in a TextView


            // TODO - Display Time and Date.
            // Hint - use ToDoItem.FORMAT.format(toDoItem.getDate()) to get date and time String


            // TODO - Set up Status CheckBox


            statusView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {


                    // TODO - Set up and implement an OnCheckedChangeListener
                    // is called when the user toggles the status checkbox


                }});


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(picoMontana);
                }
            });
        }
    }

}
