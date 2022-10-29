package com.example.proyecto.Room.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import com.example.proyecto.Room.Modelo.Evento;

@Dao
public interface EventoDAO {
    @Insert
    void insertEvent(Evento evento);

    @Delete
    void deleteEvent(Evento evento);
}
