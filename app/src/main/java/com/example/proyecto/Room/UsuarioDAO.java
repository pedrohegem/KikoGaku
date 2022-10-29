package com.example.proyecto.Room;


import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;


@Dao
public interface UsuarioDAO {

    @Query("SELECT * FROM usuarioEntity")
    List<UsuarioEntity> getAll();
}
