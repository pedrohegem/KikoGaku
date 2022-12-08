package com.example.proyecto.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.proyecto.utils.AppExecutors;
import com.example.proyecto.models.Location;
import com.example.proyecto.models.Weather;
import com.example.proyecto.repository.networking.APIManager;
import com.example.proyecto.repository.networking.APIManagerDelegate;
import com.example.proyecto.repository.room.DAO.LocationDAO;

import java.util.HashMap;
import java.util.Map;

public class LocationRepository implements APIManagerDelegate {
    private static final String TAG = "LocationRepository";

    private static LocationRepository sInstance;
    private LocationDAO dao;
    private APIManager apiManager;
    private Location location;
    private final Map<String, Long> lastUpdateTimeMillisMap = new HashMap<>();
    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 15000;

    private LocationRepository(LocationDAO dao) {
        this.dao = dao;
        this.apiManager = new APIManager(this);
    }

    public synchronized static LocationRepository getInstance( LocationDAO dao ){
        Log.d(TAG, "Getting Location repository");
        if (sInstance == null) {
            sInstance = new LocationRepository(dao);
            Log.d(TAG, "Made new location repository");
        }
        return sInstance;
    }

    public LiveData<Location> getLocation( String ubicacion ) {
        LiveData<Location> data = dao.getLocation(ubicacion);

        AppExecutors.getInstance().diskIO().execute( () -> {
            if(isFetchNeeded(ubicacion)) {
                Log.d(TAG, "Fetch from API is needed");
                loadData(ubicacion);
            }
        });

        return data;
    }

    public void loadData (String ubicacion ) {
        Log.d(TAG, "Loading data from API into DB...");

        location = new Location(ubicacion);
        apiManager.getEventWeather(ubicacion);

        lastUpdateTimeMillisMap.put(ubicacion, System.currentTimeMillis());
    }

    private boolean isFetchNeeded (String ubicacion) {
        Long lastFetchTimeMillis = lastUpdateTimeMillisMap.get(ubicacion);
        lastFetchTimeMillis = lastFetchTimeMillis == null ? 0L : lastFetchTimeMillis;
        long timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis;
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS || dao.getLocationNoLD(ubicacion) == null;
    }

    @Override
    public void onGetWeatherSuccess(Weather weather) {
        Log.d(TAG, "API Call returned with success...");
        location.setWeather(weather);
        dao.insertLocation(location);
    }

    @Override
    public void onGetWeatherFailure() {
        Log.d(TAG, "Error fetching from the API...");
    }
}
