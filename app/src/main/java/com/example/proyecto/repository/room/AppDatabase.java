package com.example.proyecto.repository.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.proyecto.models.Location;
import com.example.proyecto.repository.room.DAO.EventoDAO;
import com.example.proyecto.repository.room.DAO.LocationDAO;
import com.example.proyecto.repository.room.DAO.UsuarioDAO;
import com.example.proyecto.models.Evento;
import com.example.proyecto.models.Usuario;


@Database(entities = {Usuario.class, Evento.class, Location.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDataBase;
    private static Context contexto;
    private static Usuario usuario;

    public abstract UsuarioDAO usuarioDAO();
    public abstract EventoDAO eventoDAO();
    public abstract LocationDAO locationDAO();

    protected AppDatabase(){
    }

    public static AppDatabase getInstance(Context context){
        if (appDataBase == null) {
            appDataBase = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "database.db").fallbackToDestructiveMigration().build();
        }
        return appDataBase;
    }

    public static Usuario getUsuario() {return usuario;}

    public static void setUsuario(Usuario usuario) {AppDatabase.usuario = usuario;}
}
