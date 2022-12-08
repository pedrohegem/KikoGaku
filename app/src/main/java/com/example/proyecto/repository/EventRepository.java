package com.example.proyecto.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.proyecto.utils.AppExecutors;
import com.example.proyecto.models.Evento;
import com.example.proyecto.models.Montana;
import com.example.proyecto.models.Weather;
import com.example.proyecto.repository.networking.APIManager;
import com.example.proyecto.repository.networking.APIManagerDelegate;
import com.example.proyecto.repository.room.DAO.EventoDAO;
import com.example.proyecto.utils.ForecastDay;
import com.example.proyecto.utils.JsonSingleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventRepository implements APIManagerDelegate{
    private static final String TAG = "EventRepository";

    private static EventRepository sInstance;
    private APIManager apiManager;
    private EventoDAO dao;

    private Evento evento;
    private final Map<Integer, Long> lastUpdateTimeMillisMap = new HashMap<>();
    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 45000; //TODO Cambiar el tiempo de fetch a uno más adecuado

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

    public LiveData<List<Evento>> getAllEvents() {
        return dao.getAll();
    }

    public LiveData<Evento> getEventByID(int id) {
        LiveData<Evento> data = dao.getEventByID(id);

        if(isFetchNeeded(new Integer(id))){
            Log.d(TAG, "Fetch from API is needed");
            loadData(id);
        }
        return data;
    }

    private void loadData(int id) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            Log.d(TAG, "Loading data from API into DB...");
            evento = dao.getEvent(id).get(0);

            manageAPICalls(evento);

            lastUpdateTimeMillisMap.put(new Integer(id), System.currentTimeMillis());
        });
    }

    public void manageAPICalls(Evento e){
        int days = ForecastDay.getCallDay(e.getFecha());

        if(days == 0) { // Get weather from today
            Log.d(TAG, "Loading weather from todays...");
            if(e.getEsMunicipio()) {
                apiManager.getEventWeather(e.getUbicacion());
            } else {
                Montana m = JsonSingleton.getInstance().montanaMap.get(e.getUbicacion());
                apiManager.getEventWeather(m.getLatitud(),m.getLongitud());
            }
        } else if (days > 0 && days < 5) { // Get weather forecast
            Log.d(TAG, "Loading weather from forecast...");
            if(e.getEsMunicipio()) {
                apiManager.getEventForecast(e.getUbicacion(), days);
            } else {
                Montana m = JsonSingleton.getInstance().montanaMap.get(e.getUbicacion());
                apiManager.getEventForecast(m.getLatitud(),m.getLongitud(), days);
            }
        }
    }

    public int insertEvent ( Evento evento ) {
        return (int) dao.insertEvent(evento);
    }

    public void deleteEvent ( int id) {
        Evento e = dao.getEvent(id).get(0);
        dao.deleteEvent(e);
    }

    public void modifyEvent (Evento e){
        evento = e;
        manageAPICalls(evento);
    }

    private boolean isFetchNeeded(Integer id) {
        Long lastFetchTimeMillis = lastUpdateTimeMillisMap.get(id);
        lastFetchTimeMillis = lastFetchTimeMillis == null ? 0L : lastFetchTimeMillis;
        long timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis;
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS;
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
