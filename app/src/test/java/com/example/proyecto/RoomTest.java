package com.example.proyecto;


import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.Modelo.Evento;
import com.example.proyecto.Room.Modelo.Usuario;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class RoomTest {

    private AppDatabase db;

    @Before
    public void setUp(){
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), AppDatabase.class).allowMainThreadQueries().build();

    }

    @Test
    public void getUsuarioDAOTest(){
        Assert.assertNotNull(db.usuarioDAO());
    }

    @Test
    public void getEventoDAOTest(){
        Assert.assertNotNull(db.eventoDAO());
    }

    //Tests de los métodos CRUD de evento
    @Test
    public void testCrearEvento(){
        Evento evento = new Evento("Nombre del evento", "ubicación", "descripción", new Date(System.currentTimeMillis()), true);
        int ide;

        ide = (int)db.eventoDAO().insertEvent(evento);
        evento.setIde(ide);

        Evento recuperado = db.eventoDAO().getAll().get(0);

        Assert.assertEquals(evento.getIde(), recuperado.getIde());

        Evento evento2 = new Evento("Nombre del evento 2", "ubicación", "descripción", new Date(System.currentTimeMillis()), true);

        ide = (int)db.eventoDAO().insertEvent(evento2);
        evento2.setIde(ide);

        recuperado = db.eventoDAO().getEvent("Nombre del evento 2").get(0);

        Assert.assertEquals(evento2.getIde(), recuperado.getIde());

        db.eventoDAO().deleteEvent(evento);
        db.eventoDAO().deleteEvent(evento2);
    }

    @Test
    public void testGetAllEvento(){
        Evento municipio = new Evento("Evento de municipio 1", "ubicación", "descripción", new Date(System.currentTimeMillis()+20), true);
        Evento montana = new Evento("Evento de montaña 1", "ubicación", "descripción", new Date(System.currentTimeMillis()+200), false);
        Evento municipio2 = new Evento("Evento de municipio 2", "ubicación", "descripción 2", new Date(System.currentTimeMillis()+100), true);
        int ide;

        ide = (int)db.eventoDAO().insertEvent(municipio);
        municipio.setIde(ide);

        ide = (int)db.eventoDAO().insertEvent(municipio2);
        municipio2.setIde(ide);

        ide = (int)db.eventoDAO().insertEvent(montana);
        montana.setIde(ide);

        List<Evento> eventos = db.eventoDAO().getAll();

        Assert.assertEquals(eventos.get(0).getIde(), municipio.getIde());
        Assert.assertEquals(eventos.get(1).getIde(), municipio2.getIde());
        Assert.assertEquals(eventos.get(2).getIde(), montana.getIde());

        db.eventoDAO().deleteEvent(municipio);
        db.eventoDAO().deleteEvent(municipio2);
        db.eventoDAO().deleteEvent(montana);
    }

    @Test
    public void testModificarEvento(){
        Evento evento = new Evento("Nombre del evento", "ubicación", "descripción", new Date(System.currentTimeMillis()), true);

        int id = (int)db.eventoDAO().insertEvent(evento);
        evento.setIde(id);
        evento.setTitulo("Evento modificado");

        db.eventoDAO().updateEvent(evento);

        Evento modificado = db.eventoDAO().getAll().get(0);

        Assert.assertEquals("Evento modificado", modificado.getTitulo());

        db.eventoDAO().deleteEvent(modificado);
    }

    @Test
    public void testGetEvento(){
        Evento evento = new Evento("Nombre del evento", "ubicación", "descripción", new Date(System.currentTimeMillis()), true);
        Evento evento2 = new Evento("Nombre del evento 2", "ubicación", "descripción", new Date(System.currentTimeMillis()), false);

        int id = (int)db.eventoDAO().insertEvent(evento);
        evento.setIde(id);

        int id2 = (int)db.eventoDAO().insertEvent(evento2);
        evento2.setIde(id2);

        //Test de getEvento
        Evento recuperado = db.eventoDAO().getEvent(id2).get(0);
        Assert.assertEquals(id2, recuperado.getIde());
        Assert.assertEquals(evento2.getIde(), recuperado.getIde());

        recuperado = db.eventoDAO().getEvent("Nombre del evento").get(0);
        Assert.assertEquals("Nombre del evento", recuperado.getTitulo());
        Assert.assertEquals(evento.getTitulo(), recuperado.getTitulo());

        //Test de getMunicipios
        recuperado = db.eventoDAO().getMunicipios().get(0);
        Assert.assertEquals(true, recuperado.getEsMunicipio());
        Assert.assertEquals(evento.getIde(), recuperado.getIde());

        Assert.assertEquals(1, db.eventoDAO().getMunicipios().size());

        //Test de getMontañas
        recuperado = db.eventoDAO().getMontanas().get(0);
        Assert.assertEquals(false, recuperado.getEsMunicipio());
        Assert.assertEquals(evento2.getIde(), recuperado.getIde());

        Assert.assertEquals(1, db.eventoDAO().getMontanas().size());

        db.eventoDAO().deleteEvent(evento);
        db.eventoDAO().deleteEvent(evento2);
    }

    @Test
    public void testBorrarEvento(){
        Evento evento = new Evento("Nombre del evento", "ubicación", "descripción", new Date(System.currentTimeMillis()), true);

        int ide = (int)db.eventoDAO().insertEvent(evento);
        evento.setIde(ide);

        db.eventoDAO().deleteEvent(evento);

        Assert.assertEquals(db.eventoDAO().getAll().size(), 0);
    }

    //Test de CRUD de usuarios

    @Test
    public void testRegistrarObtenerUsuario(){
        Usuario u = new Usuario(0, "Nombre de usuario", "Contraseña", false);

        db.usuarioDAO().registerUser(u);

        Usuario registrado = db.usuarioDAO().login("Nombre de usuario", "Contraseña");

        Assert.assertEquals(u.getUsername(), registrado.getUsername());
        Assert.assertEquals(u.getPassword(), registrado.getPassword());

        db.usuarioDAO().deleteUser(registrado);
    }

    @Test
    public void testIniciarCerrarSesionGetConectado(){
        Usuario u = new Usuario(0, "Nombre de usuario", "Contraseña", false);

        db.usuarioDAO().registerUser(u);
        Usuario recuperado = db.usuarioDAO().login("Nombre de usuario", "Contraseña");

        db.usuarioDAO().activarEstadoConexion(true, recuperado.getIdu());
        recuperado = db.usuarioDAO().login("Nombre de usuario", "Contraseña");

        Assert.assertEquals(true, recuperado.isConectado());

        recuperado = db.usuarioDAO().usuarioConectado(true);

        Assert.assertEquals(true, recuperado.isConectado());

        db.usuarioDAO().activarEstadoConexion(false, recuperado.getIdu());
        recuperado = db.usuarioDAO().login("Nombre de usuario", "Contraseña");

        Assert.assertEquals(false, recuperado.isConectado());

        recuperado = db.usuarioDAO().usuarioConectado(false);

        Assert.assertEquals(false, recuperado.isConectado());

        db.usuarioDAO().deleteUser(recuperado);
    }

    @Test
    public void testModificarUsuario(){
        Usuario u = new Usuario(0, "Nombre de usuario", "Contraseña", false);

        db.usuarioDAO().registerUser(u);
        Usuario recuperado = db.usuarioDAO().login("Nombre de usuario", "Contraseña");

        recuperado.setUsername("Nombre modificado");
        db.usuarioDAO().modificarUsuario(recuperado);

        Usuario modificado = db.usuarioDAO().login("Nombre modificado", "Contraseña");
        Assert.assertEquals("Nombre modificado", modificado.getUsername());
        Assert.assertEquals(recuperado.getUsername(), modificado.getUsername());

        db.usuarioDAO().deleteUser(modificado);
    }

    @Test
    public void testBorrarUsuario(){
        Usuario u = new Usuario(0, "Nombre de usuario", "Contraseña", false);

        db.usuarioDAO().registerUser(u);
        u = db.usuarioDAO().login("Nombre de usuario", "Contraseña");

        db.usuarioDAO().deleteUser(u);

        Assert.assertNull(db.usuarioDAO().login("Nombre de usuario", "Contraseña"));
    }

    //Test de los métodos getUsuario y setUsuario
    @Test
    public void testSetGetUsuario(){
        Usuario u = new Usuario(0, "Nombre de usuario", "Contraseña", false);

        AppDatabase.setUsuario(u);

        Usuario recuperado = AppDatabase.getUsuario();

        Assert.assertEquals(u.getIdu(), recuperado.getIdu());

        Usuario diferente = new Usuario(1, "Otro", "No", false);

        Assert.assertNotEquals(recuperado.getIdu(), diferente.getIdu());
    }
}
