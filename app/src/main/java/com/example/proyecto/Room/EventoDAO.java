package com.example.proyecto.Room;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface EventoDAO {
    @Query("SELECT * FROM eventoEntity")
    List<EventoEntity> getAll();

    @Insert
    public void insertar(EventoEntity e);

    //todo cambiar codigo por el codigo del evento
    @Query("SELECT * FROM eventoEntity WHERE codigo = (:codigo)")
    public List<EventoEntity> obtenerEvento(String codigo);


    @Query("SELECT * FROM eventoEntity WHERE codigoMunicipio = (:codigoMunicipio)")
    public List<EventoEntity> obtenerPorMunicipio(String codigoMunicipio);

    @Delete
    public void borrar(EventoEntity e);
}