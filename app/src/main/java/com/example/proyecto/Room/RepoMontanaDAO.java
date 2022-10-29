package com.example.proyecto.Room;



import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RepoMontanaDAO {
    @Insert
    void insertAll(RepoMontana... reposMontana);

    @Query("SELECT * FROM repomontana")
    List<RepoMontana> getAll();


}
