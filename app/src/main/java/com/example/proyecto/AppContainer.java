package com.example.proyecto;

import android.content.Context;

import com.example.proyecto.repository.EventRepository;
import com.example.proyecto.repository.LocationRepository;
import com.example.proyecto.repository.UserRepository;
import com.example.proyecto.repository.room.AppDatabase;
import com.example.proyecto.viewmodels.BorrarPerfilViewModelFactory;
import com.example.proyecto.viewmodels.DetallesEventoViewModelFactory;
import com.example.proyecto.viewmodels.DetallesLocalizacionViewModelFactory;
import com.example.proyecto.viewmodels.IniciarSesionViewModelFactory;
import com.example.proyecto.viewmodels.ListaEventosViewModelFactory;
import com.example.proyecto.viewmodels.MainUsuarioViewModel;
import com.example.proyecto.viewmodels.MainUsuarioViewModelFactory;
import com.example.proyecto.viewmodels.ModificarEventoViewModelFactory;
import com.example.proyecto.viewmodels.PerfilViewModelFactory;
import com.example.proyecto.viewmodels.RegistrarseViewModelFactory;
import com.example.proyecto.viewmodels.TiempoActualViewModel;
import com.example.proyecto.viewmodels.TiempoActualViewModelFactory;


public class AppContainer {

    private AppDatabase database;
    public EventRepository eventRepository;
    public LocationRepository locationRepository;
    public UserRepository userRepository;
    public ListaEventosViewModelFactory listaEventosViewModelFactory;
    public DetallesEventoViewModelFactory detallesEventoViewModelFactory;
    public TiempoActualViewModelFactory tiempoActualViewModelFactory;
    public DetallesLocalizacionViewModelFactory detallesLocalizacionViewModelFactory;
    public IniciarSesionViewModelFactory iniciarSesionViewModelFactory;
    public RegistrarseViewModelFactory registrarseViewModelFactory;
    public PerfilViewModelFactory perfilViewModelFactory;
    public MainUsuarioViewModelFactory mainUsuarioViewModelFactory;
    public BorrarPerfilViewModelFactory borrarPerfilViewModelFactory;
    public ModificarEventoViewModelFactory modificarEventoMontanaViewModelFactory;

    public AppContainer(Context context){
        database = AppDatabase.getInstance(context);
        eventRepository = EventRepository.getInstance(database.eventoDAO());
        locationRepository = LocationRepository.getInstance(database.locationDAO());
        userRepository = UserRepository.getInstance(database.usuarioDAO());
        listaEventosViewModelFactory = new ListaEventosViewModelFactory(eventRepository);
        detallesEventoViewModelFactory = new DetallesEventoViewModelFactory(eventRepository);
        iniciarSesionViewModelFactory = new IniciarSesionViewModelFactory(userRepository);
        registrarseViewModelFactory = new RegistrarseViewModelFactory(userRepository);
        detallesLocalizacionViewModelFactory = new DetallesLocalizacionViewModelFactory(locationRepository);
        tiempoActualViewModelFactory = new TiempoActualViewModelFactory(locationRepository);
        perfilViewModelFactory = new PerfilViewModelFactory(userRepository);
        mainUsuarioViewModelFactory = new MainUsuarioViewModelFactory(userRepository);
        borrarPerfilViewModelFactory = new BorrarPerfilViewModelFactory(userRepository);
        modificarEventoMontanaViewModelFactory = new ModificarEventoViewModelFactory(eventRepository);
    }
}
