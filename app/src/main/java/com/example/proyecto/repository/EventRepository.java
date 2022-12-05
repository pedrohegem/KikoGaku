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
    private APIManager apiManager;
    private EventoDAO dao;
    private Evento e;
    private final MutableLiveData<Integer> filterID;
    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;
    private final Map<Integer, Long> lastUpdateTimeMillisMap = new HashMap<>();

    public synchronized static EventRepository getInstance(EventoDAO dao) {
        Log.d("EventRepository", "Getting the repository");
        if (sInstance == null) {
            sInstance = new EventRepository(dao);
            Log.d("EventRepository", "Made new repository");
        }
        return sInstance;
    }

    private EventRepository(EventoDAO dao) {
        this.dao = dao;
        this.apiManager = new APIManager(this);
        this.filterID = new MutableLiveData<>();
    }

    public LiveData<List<Evento>> getAllEventos() {
        return dao.getAll();
    }

    public void setEventID (int id) {
        filterID.setValue(new Integer(id));
        //  Comprobar si es necesario un fetch desde la API
        AppExecutors.getInstance().diskIO().execute(() -> {
            if(isFetchNeeded(id)){
                doFetchEvento(id);
            }
        });
    }

    private void doFetchEvento(int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                e = dao.getEvent(id).getValue().get(0);
                if(e.getEsMunicipio()){
                    apiManager.getEventWeather(e.getUbicacion());
                } else {
                    Montana m = JsonSingleton.getInstance().montanaMap.get(e.getUbicacion());
                    apiManager.getEventWeather(m.getLatitud(),m.getLongitud());
                }
            }
        }).start();
    }

    public LiveData<List<Evento>> getEventoByID() {
        return Transformations.switchMap(filterID, dao::getEvent);
    }

    @Override
    public void onGetWeatherSuccess(Weather weather) {
        e.setWeather(weather);
        dao.updateEvent(e);
    }

    @Override
    public void onGetWeatherFailure() {
        Log.d("EventRepository", "La llamada a la API ha fallado...");
    }

    private boolean isFetchNeeded(int id) {
        Long lastFetchTimeMillis = lastUpdateTimeMillisMap.get(new Integer(id));
        lastFetchTimeMillis = lastFetchTimeMillis == null ? 0L : lastFetchTimeMillis;
        long timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis;
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS;
    }






   /* private static final String LOG_TAG = RepoRepository.class.getSimpleName();

    // For Singleton instantiation
    private static RepoRepository sInstance;
    private final EventoDAO eventoDAO;
    private final APIManager mRepoNetworkDataSource;
    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private final Map<String, Long> lastUpdateTimeMillisMap = new HashMap<>();
    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;

    private RepoRepository(RepoDao repoDao, RepoNetworkDataSource repoNetworkDataSource) {
        mRepoDao = repoDao;
        mRepoNetworkDataSource = repoNetworkDataSource;
        // LiveData that fetches repos from network
        LiveData<Repo[]> networkData = mRepoNetworkDataSource.getCurrentRepos();
        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        networkData.observeForever(newReposFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Deleting cached repos of user
                if (newReposFromNetwork.length > 0){
                    mRepoDao.deleteReposByUser(newReposFromNetwork[0].getOwner().getLogin());
                }
                // Insert our new repos into local database
                mRepoDao.bulkInsert(Arrays.asList(newReposFromNetwork));
                Log.d(LOG_TAG, "New values inserted in Room");
            });
        });
    }

    public synchronized static RepoRepository getInstance(RepoDao dao, RepoNetworkDataSource nds) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new RepoRepository(dao, nds);
            Log.d(LOG_TAG, "Made new repository");
        }
        return sInstance;
    }

    public void setUsername(final String username){
        // TODO - Set value to MutableLiveData in order to filter getCurrentRepos LiveData

        AppExecutors.getInstance().diskIO().execute(() -> {
            if (isFetchNeeded(username)) {
                doFetchRepos(username);
            }
        });
    }

    public void doFetchRepos(String username){
        Log.d(LOG_TAG, "Fetching Repos from Github");
        AppExecutors.getInstance().diskIO().execute(() -> {
            mRepoDao.deleteReposByUser(username);
            mRepoNetworkDataSource.fetchRepos(username);
            lastUpdateTimeMillisMap.put(username, System.currentTimeMillis());
        });
    }

    /**
     * Database related operations


    public LiveData<List<Repo>> getCurrentRepos() {
        // TODO - Return LiveData from Room. Use Transformation to get owner
        return new MutableLiveData<>();
    }

    /**
     * Checks if we have to update the repos data.
     * @return Whether a fetch is needed

    private boolean isFetchNeeded(String username) {
        Long lastFetchTimeMillis = lastUpdateTimeMillisMap.get(username);
        lastFetchTimeMillis = lastFetchTimeMillis == null ? 0L : lastFetchTimeMillis;
        long timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis;
        // TODO - Implement cache policy: When time has passed or no repos in cache
        return true;
    }
**/
}
