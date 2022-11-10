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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proyecto.MainActivity;
import com.example.proyecto.R;
import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.DAO.EventoDAO;
import com.example.proyecto.Room.Modelo.Evento;

import java.util.Date;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("¿Deseas eliminar el evento?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Se debe eliminar el usuario en la BD y en el Singleton de AppDataBase. Redirigir a MainActivity

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                int ide = getArguments().getInt("idEvento", 0);
                                Evento e;
                                EventoDAO eventoDao = AppDatabase.getInstance(mContext).eventoDAO();
                                e = eventoDao.getEvent(ide).get(0);

                                eventoDao.deleteEvent(e);

                                startActivity(new Intent(mContext, MainActivity.class));
                            }
                        }).start();
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