package com.example.proyecto.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.proyecto.Room.DAO.EventoDAO;
import com.example.proyecto.Room.DAO.UsuarioDAO;
import com.example.proyecto.Room.Modelo.Evento;
import com.example.proyecto.Room.Modelo.Usuario;


@Database(entities = {Usuario.class, Evento.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDataBase;
    private static Context contexto;
    private static Usuario usuario;

    public abstract UsuarioDAO usuarioDAO();
    public abstract EventoDAO eventoDAO();

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
