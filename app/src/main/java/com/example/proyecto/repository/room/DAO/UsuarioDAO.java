package com.example.proyecto.repository.room.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proyecto.models.Usuario;

@Dao
public interface UsuarioDAO {
    @Insert
    void registerUser(Usuario user);

    @Query("SELECT * FROM usuario WHERE username = (:username) AND password = (:password)")
    Usuario login(String username, String password);

    @Query("UPDATE usuario SET conectado = (:conectado) WHERE idu = (:idu)")
    void activarEstadoConexion(boolean conectado, int idu);

    @Query("SELECT * FROM usuario WHERE conectado = (:conectado)")
    LiveData<Usuario> usuarioConectado(boolean conectado);

    @Query("SELECT * FROM usuario WHERE conectado = (:conectado)")
    Usuario getConectado(boolean conectado);

    @Update
    void modificarUsuario(Usuario usuario);

    @Delete
    void deleteUser(Usuario delete);
}
