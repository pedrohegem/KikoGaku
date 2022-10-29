package com.example.proyecto.Room;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import androidx.room.Database;

@Database(entities = {UsuarioEntity.class, EventoEntity.class, EventoMontañaEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDataBase;
    private static Context contexto;
    public abstract UsuarioDAO usuarioDAO();
    public abstract EventoDAO eventoDAO();
    public abstract EventoMontañaDAO eventoMontañaDAO();

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
