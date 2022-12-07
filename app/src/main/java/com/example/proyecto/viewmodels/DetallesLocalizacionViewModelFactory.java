package com.example.proyecto.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyecto.repository.LocationRepository;

public class DetallesLocalizacionViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final LocationRepository mRepository;

    public DetallesLocalizacionViewModelFactory(LocationRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetallesLocalizacionViewModel(mRepository);
    }
}