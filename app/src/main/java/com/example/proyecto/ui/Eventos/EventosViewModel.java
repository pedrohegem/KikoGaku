package com.example.proyecto.ui.Eventos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EventosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is eventos fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}