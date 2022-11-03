package com.example.proyecto.Room.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.proyecto.Room.Modelo.EventoMontana;

import java.util.List;

@Dao
public interface EventoMontanaDAO {
    @Insert
    void insertMontana(EventoMontana eventMontana);

    @Query("Select * from eventomontana")
    List<EventoMontana> getAll();

    @Delete
    void deleteMontana(EventoMontana eventoMontana);
}
