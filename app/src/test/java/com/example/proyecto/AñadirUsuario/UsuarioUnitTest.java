package com.example.proyecto.AñadirUsuario;

import static org.junit.Assert.*;

import com.example.proyecto.Room.Modelo.Evento;
import com.example.proyecto.Room.Modelo.Usuario;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;

public class UsuarioUnitTest {

    public static Usuario instance;

    @BeforeClass
    // Se llama antes de ejecutar esta suite (conjunto de test)
    public static void initClass(){
        instance = new Usuario();
    }

    @Test
    public void getIdu() throws NoSuchFieldException, IllegalAccessException {
        final Usuario usuario = new Usuario();
        final Field field = usuario.getClass().getDeclaredField("idu");
        field.setAccessible(true);
        field.set(usuario, 1);

        //when
        final int result = usuario.getIdu();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, 1);
    }

    @Test
    public void setIdu() throws NoSuchFieldException, IllegalAccessException {
        int value = 1;
        instance.setIdu(value);
        final Field field = instance.getClass().getDeclaredField("idu");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos idu", field.get(instance), value);
    }

    @Test
    public void getUsername() throws NoSuchFieldException, IllegalAccessException {
        final Usuario usuario = new Usuario();
        final Field field = usuario.getClass().getDeclaredField("username");
        field.setAccessible(true);
        field.set(usuario, "Jorge");

        //when
        final String result = usuario.getUsername();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, "Jorge");
    }

    @Test
    public void setUsername() throws NoSuchFieldException, IllegalAccessException {
        String value = "Pedro";
        instance.setUsername(value);
        final Field field = instance.getClass().getDeclaredField("username");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos username", field.get(instance), value);
    }

    @Test
    public void getPassword() throws NoSuchFieldException, IllegalAccessException {
        final Usuario usuario = new Usuario();
        final Field field = usuario.getClass().getDeclaredField("password");
        field.setAccessible(true);
        field.set(usuario, "123456");

        //when
        final String result = usuario.getPassword();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, "123456");
    }

    @Test
    public void setPassword() throws IllegalAccessException, NoSuchFieldException {
        String value = "123444";
        instance.setPassword(value);
        final Field field = instance.getClass().getDeclaredField("password");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos password", field.get(instance), value);
    }

    @Test
    public void setConectado() throws NoSuchFieldException, IllegalAccessException {
        boolean value = true;
        instance.setConectado(value);
        final Field field = instance.getClass().getDeclaredField("conectado");
        field.setAccessible(true);
        assertEquals("No concuerdan los campos conectado", field.get(instance), value);
    }

    @Test
    public void isConectado() throws NoSuchFieldException, IllegalAccessException {
        final Usuario usuario = new Usuario();
        final Field field = usuario.getClass().getDeclaredField("conectado");
        field.setAccessible(true);
        field.set(usuario, true);

        //when
        final boolean result = usuario.isConectado();

        //then
        assertEquals("No se ha devuelto el campo correctamente", result, true);
    }
}