package com.example.proyecto.Room.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import com.example.proyecto.Room.Modelo.EventoMontana;

@Dao
public interface EventoMontanaDAO {
    @Insert
    void insertMontana(EventoMontana eventMontana);

    @Delete
    void deleteMontana(EventoMontana eventoMontana);
}
