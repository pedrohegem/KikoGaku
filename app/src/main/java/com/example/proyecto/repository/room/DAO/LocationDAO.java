package com.example.proyecto.repository.room.DAO;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proyecto.models.Location;

@Dao
public interface LocationDAO {

    @Insert(onConflict = REPLACE)
    public long insertLocation (Location location);

    @Query("SELECT * FROM location WHERE ubicacion = (:ubicacion)")
    public Location getLocationNoLD ( String ubicacion );

    @Query("SELECT * FROM location WHERE ubicacion = (:ubicacion)")
    public LiveData<Location> getLocation ( String ubicacion );

}
