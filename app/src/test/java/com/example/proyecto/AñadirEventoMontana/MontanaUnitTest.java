package com.example.proyecto.AñadirEventoMontana;

import static org.junit.Assert.*;

import com.example.proyecto.Json.Montana;
import com.example.proyecto.Json.Municipio;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Optional;

public class MontanaUnitTest {

    public static Montana instance;

    @BeforeClass
    // Se llama antes de ejecutar esta suite (conjunto de test)
    public static void initClass(){
        instance = new Montana();
    }

    @Test
    public void getLatitud() throws NoSuchFieldException, IllegalAccessException {
        final Montana montana = new Montana();
        final Field field = montana.getClass().getDeclaredField("latitud");
        field.setAccessible(true);
        field.set(montana, new Double(43.1888023262286));

        //when
        final Double result = montana.getLatitud();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, new Double(43.1888023262286));
    }

    @Test
    public void setLatitud() throws NoSuchFieldException, IllegalAccessException {
        Double value = 43.1888023262286;
        instance.setLatitud(value);
        final Field field = instance.getClass().getDeclaredField("latitud");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos latitud", field.get(instance), value);
    }

    @Test
    public void getLongitud() throws IllegalAccessException, NoSuchFieldException {
        final Montana montana = new Montana();
        final Field field = montana.getClass().getDeclaredField("longitud");
        field.setAccessible(true);
        field.set(montana, new Double(-4.31888023262286));

        //when
        final Double result = montana.getLongitud();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, new Double(-4.31888023262286));
    }

    @Test
    public void setLongitud() throws IllegalAccessException, NoSuchFieldException {
        Double value = -4.31888023262286;
        instance.setLongitud(value);
        final Field field = instance.getClass().getDeclaredField("longitud");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos longitud", field.get(instance), value);
    }

    @Test
    public void getNombre() throws NoSuchFieldException, IllegalAccessException {
        final Montana montana = new Montana();
        final Field field = montana.getClass().getDeclaredField("nombre");
        field.setAccessible(true);
        field.set(montana, "Picos de Europa");

        //when
        final String result = montana.getNombre();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, "Picos de Europa");
    }

    @Test
    public void setNombre() throws NoSuchFieldException, IllegalAccessException {
        String value = "Picos de Europa";
        instance.setNombre(value);
        final Field field = instance.getClass().getDeclaredField("nombre");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos nombre", field.get(instance), value);
    }
}