package com.example.proyecto.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyecto.repository.UserRepository;

public class BorrarPerfilViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final UserRepository mRepository;

    public BorrarPerfilViewModelFactory(UserRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new BorrarPerfilViewModel(mRepository);
    }
}