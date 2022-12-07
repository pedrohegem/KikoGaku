package com.example.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto.models.Evento;
import com.example.proyecto.repository.EventRepository;
import com.example.proyecto.ui.ListaEventos.ListaEventosFragment;

/**
 * {@link ViewModel} for {@link ListaEventosFragment}
 */
public class ModificarEventoViewModel extends ViewModel {

    private final EventRepository eventRepository;
    private LiveData<Evento> mEvento;

    public ModificarEventoViewModel(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public LiveData<Evento> getEventByID(int idEvento) {
        mEvento = eventRepository.getEventByID(idEvento);
        return mEvento;
    }

    public void updateEvent(Evento e){
        eventRepository.modifyEvent(e);
    }

}
