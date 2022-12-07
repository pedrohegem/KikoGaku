package com.example.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto.models.Location;
import com.example.proyecto.repository.LocationRepository;
import com.example.proyecto.ui.ListaEventos.ListaEventosFragment;

/**
 * {@link ViewModel} for {@link ListaEventosFragment}
 */
public class DetallesLocalizacionViewModel extends ViewModel {

    private final LocationRepository locationRepository;
    private LiveData<Location> mLocation;

    public DetallesLocalizacionViewModel(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public LiveData<Location> getLocation(String location) {
        mLocation = locationRepository.getLocation(location);
        return mLocation;
    }

}
