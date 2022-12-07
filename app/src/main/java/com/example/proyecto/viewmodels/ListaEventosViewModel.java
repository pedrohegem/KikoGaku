package com.example.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto.models.Evento;
import com.example.proyecto.repository.EventRepository;
import com.example.proyecto.ui.ListaEventos.ListaEventosFragment;
import java.util.List;

/**
 * {@link ViewModel} for {@link ListaEventosFragment}
 */
public class ListaEventosViewModel extends ViewModel {

    private final EventRepository eventRepository;
    private final LiveData<List<Evento>> mEventos;

    public ListaEventosViewModel(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        mEventos = eventRepository.getAllEvents();
    }

    public LiveData<List<Evento>> getEventos() {
        return mEventos;
    }
}
