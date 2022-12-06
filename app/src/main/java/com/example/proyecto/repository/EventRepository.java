package com.example.proyecto.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.example.proyecto.AppExecutors;
import com.example.proyecto.models.Evento;
import com.example.proyecto.models.Montana;
import com.example.proyecto.models.Weather;
import com.example.proyecto.repository.networking.APIManager;
import com.example.proyecto.repository.networking.APIManagerDelegate;
import com.example.proyecto.repository.room.DAO.EventoDAO;
import com.example.proyecto.utils.JsonSingleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventRepository implements APIManagerDelegate{
    private static final String TAG = "EventRepository";

    private static EventRepository sInstance;
    private APIManager apiManager = new APIManager(this);
    private EventoDAO dao;
    private Evento evento;
    private final Map<Integer, Long> lastUpdateTimeMillisMap = new HashMap<>();
    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 15000;

    private EventRepository(EventoDAO dao) {
        this.dao = dao;
        this.apiManager = new APIManager(this);
    }

    public synchronized static EventRepository getInstance(EventoDAO dao) {
        Log.d(TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new EventRepository(dao);
            Log.d(TAG, "Made new repository");
        }
        return sInstance;
    }

    public LiveData<Evento> getEventByID(int id) {
        LiveData<Evento> data = dao.getEventByID(id);
        if(isFetchNeeded(new Integer(id))){
            Log.d(TAG, "Fetch from API is needed");
            loadData(id);
        }
        return data;
    }

    private boolean isFetchNeeded(Integer id) {
        Long lastFetchTimeMillis = lastUpdateTimeMillisMap.get(id);
        lastFetchTimeMillis = lastFetchTimeMillis == null ? 0L : lastFetchTimeMillis;
        long timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis;
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS;
    }

    public void loadData(int id) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            // Get location from event to fetch weather from API
            evento = dao.getEvent(id).get(0);
            apiManager.getEventWeather(evento.getUbicacion());
            lastUpdateTimeMillisMap.put(new Integer(id), System.currentTimeMillis());
        });

    }

    @Override
    public void onGetWeatherSuccess(Weather weather) {
        Log.d(TAG, "API Call returned with success...");
        evento.setWeather(weather);
        dao.updateEvent(evento);
    }

    @Override
    public void onGetWeatherFailure() {
        Log.d(TAG, "Error fetching from the API...");
    }
}
