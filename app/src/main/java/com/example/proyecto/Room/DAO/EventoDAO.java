package com.example.proyecto.Room.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.proyecto.Room.Modelo.Evento;

import java.util.List;

@Dao
public interface EventoDAO {
    @Insert
    void insertEvent(Evento evento);

    @Query("Select * from evento")
    List<Evento> getAll();


    @Delete
    void deleteEvent(Evento evento);
}
