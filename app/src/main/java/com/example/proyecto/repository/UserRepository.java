package com.example.proyecto.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import com.example.proyecto.models.Usuario;
import com.example.proyecto.repository.room.DAO.UsuarioDAO;

public class UserRepository {
    private static final String TAG = "UserRepository";
    private static UserRepository sInstance;
    private final UsuarioDAO dao;

    private UserRepository(UsuarioDAO dao) {
        this.dao = dao;
    }

    public synchronized static UserRepository getInstance( UsuarioDAO dao ){
        Log.d(TAG, "Getting UserRepository repository");
        if (sInstance == null) {
            sInstance = new UserRepository(dao);
            Log.d(TAG, "Made new UserRepository repository");
        }
        return sInstance;
    }

    public void deleteUsuario () {
        Log.d(TAG, "Operación deleteUsuario");
        // Se obtiene el usuario del perfil a borrar
        Usuario user = getUserConectado();
        dao.deleteUser(user);
    }

    public void modifyUsuario (Usuario usuario){
        Log.d(TAG, "Operación modifyUsuario");
        dao.modificarUsuario(usuario);
    }

    public LiveData<Usuario> getUser() {
        Log.d(TAG, "Operación GetUser");
        return dao.usuarioConectado(true);
    }

    public Usuario getUserConectado(){
        Log.d(TAG, "Operación getUserConectado");
        return dao.getConectado(true);
    }

    public void registerUser(Usuario user){
        Log.d(TAG, "Operación registerUser");
        this.dao.registerUser(user);
    }

    public Usuario login(String username, String password){
        Log.d(TAG, "Operación login");
        return dao.login(username, password);
    }

    public void activarEstadoConex(boolean conectado, int user){
        Log.d(TAG, "Operación activarEstadoConex");
        dao.activarEstadoConexion(conectado, user);
    }
}