package com.example.proyecto.ui.perfil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.proyecto.AppContainer;

import com.example.proyecto.MainActivity;
import com.example.proyecto.MyApplication;
import com.example.proyecto.models.Location;
import com.example.proyecto.models.Usuario;
import com.example.proyecto.databinding.FragmentPerfilBinding;
import com.example.proyecto.repository.LocationRepository;
import com.example.proyecto.repository.room.AppDatabase;
import com.example.proyecto.viewmodels.DetallesLocalizacionViewModel;
import com.example.proyecto.viewmodels.PerfilViewModel;

public class PerfilFragment extends Fragment {

    private static String TAG = "PerfilFragment";

    private FragmentPerfilBinding binding;
    private Context mContext;

    private EditText eUsername;
    private EditText eNewPassword;
    private EditText eCurrentPassword;

    private Usuario usuario;

    PerfilViewModel mViewModel;
    private AppContainer appContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtengo el usuario de la base de datos y se lo inserto al EditText de username
        appContainer = ((MyApplication) mContext.getApplicationContext()).appContainer;

        final Observer<Usuario> observer = new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario user) {
                Log.d(TAG, "Data changed on observer...");
                if(user != null) {
                    eUsername = binding.username;
                    eCurrentPassword = binding.currentPassword;
                    eNewPassword = binding.newPassword;
                    eUsername.setText(user.getUsername());
                    usuario = user;
                }
            }
        };

        mViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.perfilViewModelFactory).get(PerfilViewModel.class);

        mViewModel.getUser().observeForever(observer);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button bGuardar = binding.bGuardar;

        // Implementamos la lógica del botón
        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Se comprueba si los campos estan vacios
                if(!eUsername.getText().toString().isEmpty() && !eCurrentPassword.getText().toString().isEmpty() && !eNewPassword.getText().toString().isEmpty()){

                    // Si la contraseña actual introducida coincide con el del usuario de BD, entonces se modifica el de la base de datos
                    if(eCurrentPassword.getText().toString().equals(usuario.getPassword())){

                        // Se actualizan los datos del usuario
                        usuario.setUsername(eUsername.getText().toString());
                        usuario.setPassword(eNewPassword.getText().toString());

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mViewModel.modifyUser(usuario);

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