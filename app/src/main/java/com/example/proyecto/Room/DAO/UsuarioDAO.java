package com.example.proyecto.Room.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import com.example.proyecto.Room.Modelo.Usuario;

@Dao
public interface UsuarioDAO {
    @Insert
    void insertUser(Usuario user);

    @Delete
    void deleteUser(Usuario delete);
}
