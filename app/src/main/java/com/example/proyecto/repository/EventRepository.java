package com.example.proyecto.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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

public class EventRepository implements APIManagerDelegate {

    private static EventRepository sInstance;
    private static final String TAG = "EventRepository";
    private APIManager apiManager;
    private EventoDAO dao;

    private EventRepository(EventoDAO dao) { this.dao = dao; }

    public synchronized static EventRepository getInstance(EventoDAO dao) {
        Log.d(TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new EventRepository(dao);
            Log.d(TAG, "Made new repository");
        }
        return sInstance;
    }

    public LiveData<Evento> getEventByID(int id) {
        return dao.getEventByID(id);
        // TODO Comprobar si es necesario fetch de API
    }

    @Override
    public void onGetWeatherSuccess(Weather weather) {
        // TODO Gestionar carga desde API
    }

    @Override
    public void onGetWeatherFailure() {
       // TODO Gestionar fallo de carga desde API
    }


}
