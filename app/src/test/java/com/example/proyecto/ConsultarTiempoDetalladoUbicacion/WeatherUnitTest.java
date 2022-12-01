package com.example.proyecto.ConsultarTiempoDetalladoUbicacion;

import static org.junit.Assert.*;

import com.example.proyecto.Room.Modelo.Evento;
import com.example.proyecto.Room.Modelo.Weather;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;

public class WeatherUnitTest {


    public static Weather instance;

    @BeforeClass
    // Se llama antes de ejecutar esta suite (conjunto de test)
    public static void initClass(){
        instance = new Weather();
    }

    @Test
    public void getEstadoTiempoMay() throws NoSuchFieldException, IllegalAccessException {
        final Weather evento = new Weather();
        final Field field = evento.getClass().getDeclaredField("estadoTiempo");
        field.setAccessible(true);
        field.set(evento, "Soleado");

        //when
        final String result = evento.getEstadoTiempo();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, "Soleado");
    }

    @Test
    public void getDescEstadoTiempoMay() throws NoSuchFieldException, IllegalAccessException {
        final Weather evento = new Weather();
        final Field field = evento.getClass().getDeclaredField("descEstadoTiempo");
        field.setAccessible(true);
        field.set(evento, "Hace sol. Cielo despejado");

        //when
        final String result = evento.getDescEstadoTiempo();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, "Hace sol. Cielo despejado");
    }

    @Test
    public void getCiudad() throws NoSuchFieldException, IllegalAccessException {
        final Weather evento = new Weather();
        final Field field = evento.getClass().getDeclaredField("ciudad");
        field.setAccessible(true);
        field.set(evento, "Mérida");

        //when
        final String result = evento.getCiudad();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, "Mérida");
    }

    @Test
    public void setCiudad() throws NoSuchFieldException, IllegalAccessException {
        String value = "Cáceres";
        instance.setCiudad(value);
        final Field field = instance.getClass().getDeclaredField("ciudad");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos ciudad", field.get(instance), value);
    }

    @Test
    public void getTemperatura() throws NoSuchFieldException, IllegalAccessException {
        final Weather evento = new Weather();
        final Field field = evento.getClass().getDeclaredField("temperatura");
        field.setAccessible(true);
        field.set(evento, 30);

        //when
        final int result = evento.getTemperatura();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, 30);
    }

    @Test
    public void setTemperatura() throws NoSuchFieldException, IllegalAccessException {
        int value = 25;
        instance.setTemperatura(value);
        final Field field = instance.getClass().getDeclaredField("temperatura");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos temperatura", field.get(instance), value);
    }

    @Test
    public void getSensTermica() throws IllegalAccessException, NoSuchFieldException {
        final Weather evento = new Weather();
        final Field field = evento.getClass().getDeclaredField("sensTermica");
        field.setAccessible(true);
        field.set(evento, 29);

        //when
        final int result = evento.getSensTermica();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, 29);
    }

    @Test
    public void setSensTermica() throws NoSuchFieldException, IllegalAccessException {
        int value = 25;
        instance.setSensTermica(value);
        final Field field = instance.getClass().getDeclaredField("sensTermica");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos sensTermica", field.get(instance), value);
    }

    @Test
    public void getTempMinima() throws NoSuchFieldException, IllegalAccessException {
        final Weather evento = new Weather();
        final Field field = evento.getClass().getDeclaredField("tempMinima");
        field.setAccessible(true);
        field.set(evento, 10);

        //when
        final int result = evento.getTempMinima();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, 10);
    }

    @Test
    public void setTempMinima() throws NoSuchFieldException, IllegalAccessException {
        int value = 10;
        instance.setTempMinima(value);
        final Field field = instance.getClass().getDeclaredField("tempMinima");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos tempMinima", field.get(instance), value);
    }

    @Test
    public void getTempMaxima() throws NoSuchFieldException, IllegalAccessException {
        final Weather evento = new Weather();
        final Field field = evento.getClass().getDeclaredField("tempMaxima");
        field.setAccessible(true);
        field.set(evento, 40);

        //when
        final int result = evento.getTempMaxima();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, 40);
    }

    @Test
    public void setTempMaxima() throws IllegalAccessException, NoSuchFieldException {
        int value = 40;
        instance.setTempMaxima(value);
        final Field field = instance.getClass().getDeclaredField("tempMaxima");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos tempMaxima", field.get(instance), value);
    }

    @Test
    public void getPresion() throws NoSuchFieldException, IllegalAccessException {
        final Weather evento = new Weather();
        final Field field = evento.getClass().getDeclaredField("presion");
        field.setAccessible(true);
        field.set(evento, 10);

        //when
        final int result = evento.getPresion();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, 10);
    }

    @Test
    public void setPresion() throws NoSuchFieldException, IllegalAccessException {
        int value = 10;
        instance.setPresion(value);
        final Field field = instance.getClass().getDeclaredField("presion");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos presion", field.get(instance), value);
    }

    @Test
    public void getHumedad() throws NoSuchFieldException, IllegalAccessException {
        final Weather evento = new Weather();
        final Field field = evento.getClass().getDeclaredField("humedad");
        field.setAccessible(true);
        field.set(evento, 70);

        //when
        final int result = evento.getHumedad();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, 70);
    }

    @Test
    public void setHumedad() throws NoSuchFieldException, IllegalAccessException {
        int value = 70;
        instance.setHumedad(value);
        final Field field = instance.getClass().getDeclaredField("humedad");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos humedad", field.get(instance), value);
    }

    @Test
    public void getVelocidadViento() throws IllegalAccessException, NoSuchFieldException {
        final Weather evento = new Weather();
        final Field field = evento.getClass().getDeclaredField("velocidadViento");
        field.setAccessible(true);
        field.set(evento, new Double(13));

        //when
        final Double result = evento.getVelocidadViento();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, new Double(13));
    }

    @Test
    public void setVelocidadViento() throws NoSuchFieldException, IllegalAccessException {
        Double value = new Double(13);
        instance.setVelocidadViento(value);
        final Field field = instance.getClass().getDeclaredField("velocidadViento");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos velocidadViento", field.get(instance), value);
    }

    @Test
    public void getEstadoTiempo() throws NoSuchFieldException, IllegalAccessException {
        final Weather evento = new Weather();
        final Field field = evento.getClass().getDeclaredField("estadoTiempo");
        field.setAccessible(true);
        field.set(evento, "Lluvia");

        //when
        final String result = evento.getEstadoTiempo();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, "Lluvia");
    }

    @Test
    public void setEstadoTiempo() throws IllegalAccessException, NoSuchFieldException {
        String value = "Lluvia";
        instance.setEstadoTiempo(value);
        final Field field = instance.getClass().getDeclaredField("estadoTiempo");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos estadoViento", field.get(instance), value);
    }

    @Test
    public void getDescEstadoTiempo() throws NoSuchFieldException, IllegalAccessException {
        final Weather evento = new Weather();
        final Field field = evento.getClass().getDeclaredField("descEstadoTiempo");
        field.setAccessible(true);
        field.set(evento, "Lluvia de una borrasca proveniente del Oeste peninsular");

        //when
        final String result = evento.getDescEstadoTiempo();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, "Lluvia de una borrasca proveniente del Oeste peninsular");
    }

    @Test
    public void setDescEstadoTiempo() throws NoSuchFieldException, IllegalAccessException {
        String value = "Lluvia de una borrasca proveniente del Oeste peninsular";
        instance.setDescEstadoTiempo(value);
        final Field field = instance.getClass().getDeclaredField("descEstadoTiempo");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos descEstadoViento", field.get(instance), value);
    }

    @Test
    public void getGifResource() throws NoSuchFieldException, IllegalAccessException {
        final Weather evento = new Weather();
        final Field field = evento.getClass().getDeclaredField("gifResource");
        field.setAccessible(true);
        field.set(evento, 1);

        //when
        final int result = evento.getGifResource();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, 1);
    }

    @Test
    public void setGifResource() throws NoSuchFieldException, IllegalAccessException {
        int value = 1;
        instance.setGifResource(value);
        final Field field = instance.getClass().getDeclaredField("gifResource");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos gifResource", field.get(instance), value);
    }
}