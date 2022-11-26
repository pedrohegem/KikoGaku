package com.example.proyecto.AñadirEventoMunicipio;

import static org.junit.Assert.*;

import com.example.proyecto.Json.Municipio;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;

public class MunicipioUnitTest {

    public static Municipio instance;

    @BeforeClass
    // Se llama antes de ejecutar esta suite (conjunto de test)
    public static void initClass(){
        instance = new Municipio();
    }

    @Test
    public void getCodigo() throws NoSuchFieldException, IllegalAccessException {
        final Municipio municipio = new Municipio();
        final Field field = municipio.getClass().getDeclaredField("codigo");
        field.setAccessible(true);
        field.set(municipio, "01051");

        //when
        final String result = municipio.getCodigo();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, "01051");
    }

    @Test
    public void setCodigo() throws NoSuchFieldException, IllegalAccessException {
        String value = "01051";
        instance.setCodigo(value);
        final Field field = instance.getClass().getDeclaredField("codigo");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos codigo", field.get(instance), value);
    }

    @Test
    public void getMunicipio() throws IllegalAccessException, NoSuchFieldException {
        final Municipio municipio = new Municipio();
        final Field field = municipio.getClass().getDeclaredField("municipio");
        field.setAccessible(true);
        field.set(municipio, "Sevilla");

        //when
        final String result = municipio.getMunicipio();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, "Sevilla");
    }

    @Test
    public void setMunicipio() throws NoSuchFieldException, IllegalAccessException {
        String value = "Sevilla";
        instance.setMunicipio(value);
        final Field field = instance.getClass().getDeclaredField("municipio");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos municipio", field.get(instance), value);
    }

    @Test
    public void getProvincia() throws NoSuchFieldException, IllegalAccessException {
        final Municipio municipio = new Municipio();
        final Field field = municipio.getClass().getDeclaredField("provincia");
        field.setAccessible(true);
        field.set(municipio, "Sevilla");

        //when
        final String result = municipio.getProvincia();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, "Sevilla");
    }

    @Test
    public void setProvincia() throws NoSuchFieldException, IllegalAccessException {
        String value = "Sevilla";
        instance.setProvincia(value);
        final Field field = instance.getClass().getDeclaredField("provincia");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos provincia", field.get(instance), value);
    }
}