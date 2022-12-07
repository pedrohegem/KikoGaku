package com.example.proyecto;

import android.content.Context;

import com.example.proyecto.repository.EventRepository;
import com.example.proyecto.repository.room.AppDatabase;
import com.example.proyecto.viewmodels.DetallesEventoViewModelFactory;
import com.example.proyecto.viewmodels.ListaEventosViewModelFactory;
import com.example.proyecto.viewmodels.ModificarEventoMontanaViewModelFactory;


public class AppContainer {

    private AppDatabase database;
    public EventRepository eventRepository;
    public ListaEventosViewModelFactory listaEventosViewModelFactory;
    public DetallesEventoViewModelFactory detallesEventoViewModelFactory;
    public ModificarEventoMontanaViewModelFactory modificarEventoMontanaViewModelFactory;

    public AppContainer(Context context){
        database = AppDatabase.getInstance(context);
        eventRepository = EventRepository.getInstance(database.eventoDAO());
        listaEventosViewModelFactory = new ListaEventosViewModelFactory(eventRepository);
        detallesEventoViewModelFactory = new DetallesEventoViewModelFactory(eventRepository);
        modificarEventoMontanaViewModelFactory = new ModificarEventoMontanaViewModelFactory(eventRepository);
    }
}
