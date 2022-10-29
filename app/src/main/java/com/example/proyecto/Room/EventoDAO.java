package com.example.proyecto.Room;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventoDAO {
    @Query("SELECT * FROM eventoEntity")
    List<EventoEntity> getAll();
}
