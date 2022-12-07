package com.example.proyecto.repository.room.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proyecto.models.Location;

@Dao
public interface LocationDAO {

    @Insert
    public long insertLocation (Location location);

    @Query("SELECT * FROM location WHERE ubicacion = (:ubicacion)")
    public Location getLocationNoLD ( String ubicacion );

    @Query("SELECT * FROM location WHERE ubicacion = (:ubicacion)")
    public LiveData<Location> getLocation ( String ubicacion );

    @Update
    public void updateLocation ( Location location );
}
