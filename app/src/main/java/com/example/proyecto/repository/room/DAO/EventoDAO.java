package com.example.proyecto.repository.room.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proyecto.models.Evento;

import java.util.List;

@Dao
public interface EventoDAO {
    @Insert
    long insertEvent(Evento evento);

    @Query("SELECT * FROM evento")
    LiveData<List<Evento>> getAll();

    @Query("SELECT * FROM evento WHERE esMunicipio <> 0")
    List<Evento> getMunicipios();

    @Query("SELECT * FROM evento WHERE esMunicipio = 0")
    List<Evento> getMontanas();

    @Query("SELECT * FROM evento WHERE ide = (:idEvento)")
    List<Evento> getEvent(int idEvento);

    @Query("SELECT * FROM evento WHERE ide = (:idEvento)")
    LiveData<Evento> getEventByID(int idEvento);

    @Query("SELECT * FROM evento WHERE titulo = (:tituloEvento)")
    List<Evento> getEvent(String tituloEvento);

    @Update
    void updateEvent(Evento evento);

    @Delete
    void deleteEvent(Evento evento);
}
