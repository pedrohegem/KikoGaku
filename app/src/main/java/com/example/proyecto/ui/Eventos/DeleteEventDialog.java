package com.example.proyecto.ui.Eventos;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextThemeWrapper;
import android.widget.Toast;

import com.example.proyecto.utils.AppExecutors;
import com.example.proyecto.MainActivity;
import com.example.proyecto.repository.EventRepository;
import com.example.proyecto.repository.room.AppDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteEventDialog} factory method to
 * create an instance of this fragment.
 */
public class DeleteEventDialog extends androidx.fragment.app.DialogFragment {

    private Context mContext;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), android.R.style.Theme_DeviceDefault_Dialog));

        builder.setMessage("¿Deseas eliminar el evento?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AppExecutors.getInstance().diskIO().execute(() -> {
                            int ide = getArguments().getInt("idEvento", 0);
                            EventRepository.getInstance(AppDatabase.getInstance(mContext).eventoDAO()).deleteEvent(ide);
                            startActivity(new Intent(mContext, MainActivity.class));
                        });
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "No", Toast.LENGTH_SHORT).show();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}