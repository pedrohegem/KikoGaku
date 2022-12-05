package com.example.proyecto.repository;

import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;

import com.example.proyecto.AppExecutors;
import com.example.proyecto.repository.networking.APIManager;
import com.example.proyecto.repository.room.DAO.EventoDAO;

public class EventRepository {
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
