package com.example.proyecto.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyecto.repository.EventRepository;
import com.example.proyecto.repository.LocationRepository;

public class TiempoActualViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final LocationRepository mRepository;

    public TiempoActualViewModelFactory(LocationRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new TiempoActualViewModel(mRepository);
    }
}