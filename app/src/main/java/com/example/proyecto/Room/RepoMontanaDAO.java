package com.example.proyecto.Room;




import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RepoMontanaDAO {
    @Insert
    void insertAll(RepoMontana... reposMontana);

    @Query("SELECT * FROM repomontana")
    List<RepoMontana> getAll();


}
