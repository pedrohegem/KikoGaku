package com.example.proyecto.Room;



import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {RepoMontana.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDataBase;
    private static Context contexto;

    public abstract RepoMontanaDAO repoMontanaDAO();

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
