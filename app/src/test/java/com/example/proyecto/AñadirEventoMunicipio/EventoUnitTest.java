package com.example.proyecto.AñadirEventoMunicipio;

import static org.junit.Assert.*;

import com.example.proyecto.Room.Modelo.Evento;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Date;

public class EventoUnitTest {

    public static Evento instance;

    @BeforeClass
    // Se llama antes de ejecutar esta suite (conjunto de test)
    public static void initClass(){
        instance = new Evento();
    }


    @Test
    public void getIde() throws NoSuchFieldException, IllegalAccessException {
        final Evento evento = new Evento();
        final Field field = evento.getClass().getDeclaredField("ide");
        field.setAccessible(true);
        field.set(evento, 1);

        //when
        final int result = evento.getIde();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, 1);
    }

    @Test
    public void setIde() throws NoSuchFieldException, IllegalAccessException {
        int value = 1;
        instance.setIde(value);
        final Field field = instance.getClass().getDeclaredField("ide");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos ide", field.get(instance), value);
    }

    @Test
    public void getTitulo() throws NoSuchFieldException, IllegalAccessException {
        final Evento evento = new Evento();
        final Field field = evento.getClass().getDeclaredField("titulo");
        field.setAccessible(true);
        field.set(evento, "Futbol");

        //when
        final String result = evento.getTitulo();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, "Futbol");
    }

    @Test
    public void setTitulo() throws NoSuchFieldException, IllegalAccessException {
        String value = "Futbol";
        instance.setTitulo(value);
        final Field field = instance.getClass().getDeclaredField("titulo");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos titulo", field.get(instance), value);
    }

    @Test
    public void getUbicacion() throws NoSuchFieldException, IllegalAccessException {
        final Evento evento = new Evento();
        final Field field = evento.getClass().getDeclaredField("ubicacion");
        field.setAccessible(true);
        field.set(evento, "Sevilla");

        //when
        final String result = evento.getUbicacion();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, "Sevilla");
    }

    @Test
    public void setUbicacion() throws NoSuchFieldException, IllegalAccessException {
        String value = "Sevilla";
        instance.setUbicacion(value);
        final Field field = instance.getClass().getDeclaredField("ubicacion");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos ubicacion", field.get(instance), value);
    }

    @Test
    public void getDescripcion() throws NoSuchFieldException, IllegalAccessException {
        final Evento evento = new Evento();
        final Field field = evento.getClass().getDeclaredField("descripcion");
        field.setAccessible(true);
        field.set(evento, "Partido de futbol con los amigos");

        //when
        final String result = evento.getDescripcion();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, "Partido de futbol con los amigos");
    }

    @Test
    public void setDescripcion() throws NoSuchFieldException, IllegalAccessException {
        String value = "Un partido con los amigos";
        instance.setDescripcion(value);
        final Field field = instance.getClass().getDeclaredField("descripcion");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos descripcion", field.get(instance), value);
    }

    @Test
    public void getEsMunicipio() throws NoSuchFieldException, IllegalAccessException {
        final Evento evento = new Evento();
        final Field field = evento.getClass().getDeclaredField("esMunicipio");
        field.setAccessible(true);
        field.set(evento, true);

        //when
        final boolean result = evento.getEsMunicipio();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, true);
    }

    @Test
    public void setEsMunicipio() throws NoSuchFieldException, IllegalAccessException {
        boolean value = true;
        instance.setEsMunicipio(value);
        final Field field = instance.getClass().getDeclaredField("esMunicipio");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos esMunicipio", field.get(instance), value);
    }

    @Test
    public void getFecha() throws NoSuchFieldException, IllegalAccessException {
        final Evento evento = new Evento();
        final Field field = evento.getClass().getDeclaredField("fecha");
        field.setAccessible(true);
        field.set(evento, new Date(2023, 1, 1));

        //when
        final Date result = evento.getFecha();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, new Date(2023, 1, 1));
    }

    @Test
    public void setFecha() throws NoSuchFieldException, IllegalAccessException {
        Date value = new Date(2021, 1, 1);
        instance.setFecha(value);
        final Field field = instance.getClass().getDeclaredField("fecha");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos fecha", field.get(instance), value);
    }

    @Test
    public void compareTo() {
        final Evento evento = new Evento();
        evento.setFecha(new Date(2022,1,1));
        instance.setFecha(new Date(2021,1,1));

        assertEquals("No se compara la fecha de dos eventos correctamente", instance.compareTo(evento), -1);
    }
}