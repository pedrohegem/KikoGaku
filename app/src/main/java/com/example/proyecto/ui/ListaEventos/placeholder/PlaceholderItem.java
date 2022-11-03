package com.example.proyecto.ui.ListaEventos.placeholder;

import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.DAO.EventoDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A placeholder item representing a piece of content.
 */
public class PlaceholderItem {
    public final String id;
    public final String nombre;
    public final String fecha;

    public PlaceholderItem(String id, String nombre,String fech) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fech;

    }

    @Override
    public String toString() {
        return nombre+fecha;
    }
}