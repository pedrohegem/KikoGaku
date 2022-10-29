package com.example.proyecto.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


import java.util.List;

@Dao
public interface EventoDAO {
    @Query("SELECT * FROM eventoEntity")
    List<EventoEntity> getAll();

    @Insert
    public void insertar(EventoEntity e);

    //todo cambiar codigo por el codigo del evento
    @Query("SELECT * FROM eventoEntity WHERE ide = (:codigo)")
    public List<EventoEntity> obtenerEvento(int codigo);




    @Delete
    public void borrar(EventoEntity e);
}