package com.example.proyecto.Room;



import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EventoMontanaDAO {
    @Query("SELECT * FROM eventoMontanaEntity")
    List<EventoMontanaEntity> getAll();
}
