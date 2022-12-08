package com.example.proyecto.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.proyecto.AppExecutors;
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

    public void deleteUsuario (Usuario usuario) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            dao.deleteUser(usuario);
        });
    }

    public void modifyUsuario (Usuario usuario){
        AppExecutors.getInstance().diskIO().execute(() -> {
            dao.modificarUsuario(usuario);
        });
    }

    public LiveData<Usuario> getUser() {
        return dao.usuarioConectado(true);
    }

    public Usuario getUserConectado(){
        return dao.getConectado(true);
    }

    public void registerUser(Usuario user){
            this.dao.registerUser(user);
    }

    public Usuario login(String username, String password){
        return dao.login(username, password);
    }

    public void activarEstadoConex(boolean conectado, int user){
        dao.activarEstadoConexion(conectado, user);
    }
}