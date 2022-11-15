package com.example.proyecto.Room.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proyecto.Room.Modelo.Evento;

import java.util.List;

@Dao
public interface EventoDAO {
    @Insert
    long insertEvent(Evento evento);

    @Query("SELECT * FROM evento")
    List<Evento> getAll();

    @Query("SELECT * FROM evento WHERE esMunicipio <> 0")
    List<Evento> getMunicipios();

    @Query("SELECT * FROM evento WHERE esMunicipio = 0")
    List<Evento> getMontanas();

    @Query("SELECT * FROM evento WHERE ide = (:idEvento)")
    List<Evento> getEvent(int idEvento);

    @Query("SELECT * FROM evento WHERE titulo = (:tituloEvento)")
    List<Evento> getEvent(String tituloEvento);

    @Update
    void updateEvent(Evento evento);

    @Delete
    void deleteEvent(Evento evento);
}
