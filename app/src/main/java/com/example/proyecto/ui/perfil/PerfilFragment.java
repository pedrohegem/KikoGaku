package com.example.proyecto.ui.perfil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.proyecto.MainActivity;
import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.DAO.UsuarioDAO;
import com.example.proyecto.Room.Modelo.Usuario;
import com.example.proyecto.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private Context mContext;

    private EditText eUsername;
    private EditText eNewPassword;
    private EditText eCurrentPassword;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Obtengo el usuario del singleton de la base de datos y se lo inserto al EditText de username
        final Usuario usuario = AppDatabase.getUsuario();

        eUsername = binding.username;
        eCurrentPassword = binding.currentPassword;
        eNewPassword = binding.newPassword;

        eUsername.setText(usuario.getUsername());

        Button bGuardar = binding.bGuardar;

        // Implementamos la lógica del botón
        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Se comprueba si los campos estan vacios
                if(!eUsername.getText().toString().isEmpty() && !eCurrentPassword.getText().toString().isEmpty() && !eNewPassword.getText().toString().isEmpty()){

                    // Si la contraseña actual coincide con el del usuario en el Singleton, entonces se modifica el de la base de datos y el del singleton
                    if(eCurrentPassword.getText().toString().equals(AppDatabase.getUsuario().getPassword())){

                        // Se actualizan los datos del usuario
                        usuario.setUsername(eUsername.getText().toString());
                        usuario.setPassword(eNewPassword.getText().toString());

                        AppDatabase.setUsuario(usuario); // Se actualiza el del singleton

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                UsuarioDAO usuarioDAO = AppDatabase.getInstance(mContext).usuarioDAO();
                                usuarioDAO.modificarUsuario(usuario);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, "Se ha modificado el perfil correctamente", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(mContext, MainActivity.class));
                                    }
                                });

                            }
                        }).start();
                    }
                    else{
                        Toast.makeText(mContext, "La contraseña actual introducida es incorrecta", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(mContext, "Rellena todos los campos de las nuevas credenciales", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Se implementa la lógica del boton eliminar cuenta
        Button bEliminar = binding.bEliminar;
        bEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
                deleteDialogFragment.show(getChildFragmentManager(), "DeleteDialogFragment");
            }
        });

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity cea = (MainActivity) getActivity();
        cea.setDayLight();
    }
}