package com.example.proyecto.ui.perfil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.proyecto.AppContainer;
import com.example.proyecto.MainActivity;
import com.example.proyecto.MyApplication;
import com.example.proyecto.viewmodels.BorrarPerfilViewModel;
import com.example.proyecto.viewmodels.RegistrarseViewModel;


public class DeleteDialogFragment extends androidx.fragment.app.DialogFragment {

    private Context mContext;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_DeviceDefault_Dialog));

        AppContainer appContainer = ((MyApplication) mContext.getApplicationContext()).appContainer;
        BorrarPerfilViewModel mViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.borrarPerfilViewModelFactory).get(BorrarPerfilViewModel.class);

        builder.setMessage("¿Deseas eliminar la cuenta?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Se debe eliminar el usuario en la BD y en el Singleton de AppDataBase. Redirigir a MainActivity

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mViewModel.deleteUsuario();

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
