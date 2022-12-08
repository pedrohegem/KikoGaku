package com.example.proyecto;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.proyecto.models.Evento;
import com.example.proyecto.models.Usuario;
import com.example.proyecto.repository.room.DAO.UsuarioDAO;

import java.util.List;

public class UserRepository {
    private static final String TAG = "UserRepository";
    private static UserRepository sInstance;
    private final UsuarioDAO dao;

    private UserRepository(UsuarioDAO dao) {
        this.dao = dao;
    }

    public synchronized static UserRepository getInstance( UsuarioDAO dao ){
        Log.d(TAG, "Getting Location repository");
        if (sInstance == null) {
            sInstance = new UserRepository(dao);
            Log.d(TAG, "Made new location repository");
        }
        return sInstance;
    }

    public void insertUsuario ( Usuario usuario ) {
        dao.registerUser(usuario);
    }

    public void deleteUsuario (Usuario usuario) {
        dao.deleteUser(usuario);
    }

    public void modifyUsuario (Usuario usuario){
        dao.modificarUsuario(usuario);
    }

    public LiveData<Usuario> getUser() {
        return dao.usuarioConectado(true);
    }
}
