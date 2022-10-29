package com.example.proyecto.Room;





import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface UsuarioDAO {

    @Query("SELECT * FROM usuarioEntity")
    List<UsuarioEntity> getAll();
}
