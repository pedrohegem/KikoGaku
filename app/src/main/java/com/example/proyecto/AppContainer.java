package com.example.proyecto;

import android.content.Context;

import com.example.proyecto.repository.EventRepository;
import com.example.proyecto.repository.LocationRepository;
import com.example.proyecto.repository.room.AppDatabase;
import com.example.proyecto.viewmodels.DetallesEventoViewModelFactory;
import com.example.proyecto.viewmodels.DetallesLocalizacionViewModelFactory;
import com.example.proyecto.viewmodels.ListaEventosViewModelFactory;
import com.example.proyecto.viewmodels.ModificarEventoViewModelFactory;
import com.example.proyecto.viewmodels.TiempoActualViewModel;
import com.example.proyecto.viewmodels.TiempoActualViewModelFactory;


public class AppContainer {

    private AppDatabase database;
    public EventRepository eventRepository;
    public LocationRepository locationRepository;
    public ListaEventosViewModelFactory listaEventosViewModelFactory;
    public DetallesEventoViewModelFactory detallesEventoViewModelFactory;
    public TiempoActualViewModelFactory tiempoActualViewModelFactory;
    public DetallesLocalizacionViewModelFactory detallesLocalizacionViewModelFactory;
    public ModificarEventoViewModelFactory modificarEventoMontanaViewModelFactory;

    public AppContainer(Context context){
        database = AppDatabase.getInstance(context);
        eventRepository = EventRepository.getInstance(database.eventoDAO());
        locationRepository = LocationRepository.getInstance(database.locationDAO());
        listaEventosViewModelFactory = new ListaEventosViewModelFactory(eventRepository);
        detallesEventoViewModelFactory = new DetallesEventoViewModelFactory(eventRepository);
        detallesLocalizacionViewModelFactory = new DetallesLocalizacionViewModelFactory(locationRepository);
        tiempoActualViewModelFactory = new TiempoActualViewModelFactory(locationRepository);
        modificarEventoMontanaViewModelFactory = new ModificarEventoViewModelFactory(eventRepository);
    }
}
