package com.example.proyecto.ui.perfil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.proyecto.AppContainer;
import com.example.proyecto.InicioSesion;
import com.example.proyecto.MainActivity;
import com.example.proyecto.MyApplication;
import com.example.proyecto.repository.UserRepository;
import com.example.proyecto.repository.room.AppDatabase;
import com.example.proyecto.repository.room.DAO.UsuarioDAO;
import com.example.proyecto.models.Usuario;

public class DeleteDialogFragment extends androidx.fragment.app.DialogFragment {

    private Context mContext;

    private UserRepository userRepository;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("¿Deseas eliminar la cuenta?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Se debe eliminar el usuario en la BD y en el Singleton de AppDataBase. Redirigir a MainActivity

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                AppContainer appContainer = ((MyApplication) mContext.getApplicationContext()).appContainer;
                                userRepository = UserRepository.getInstance(AppDatabase.getInstance(mContext).usuarioDAO());

                                Usuario user = userRepository.getUserConectado();
                                userRepository.deleteUsuario(user);
                                getActivity().runOnUiThread(() -> startActivity(new Intent(mContext, MainActivity.class)));
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
