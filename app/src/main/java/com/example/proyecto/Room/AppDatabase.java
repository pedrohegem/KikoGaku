package com.example.proyecto.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.proyecto.Json.Montana;
import com.example.proyecto.Room.DAO.EventoDAO;
import com.example.proyecto.Room.DAO.UsuarioDAO;
import com.example.proyecto.Room.Modelo.Evento;
import com.example.proyecto.Room.Modelo.EventoMontana;
import com.example.proyecto.Room.Modelo.Usuario;


@Database(entities = {Usuario.class, Evento.class, EventoMontana.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDataBase;
    private static Context contexto;

    public abstract UsuarioDAO usuarioDAO();
    public abstract EventoDAO eventoDAO();
    public abstract EventoMontana eventoMontana();

    protected AppDatabase(){
    }

    public static AppDatabase getInstance(Context context){
        if (appDataBase == null) {
            appDataBase = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "database.db").build();
        }
        return appDataBase;
    }

}
