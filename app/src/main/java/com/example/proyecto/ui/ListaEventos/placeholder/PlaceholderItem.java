package com.example.proyecto.ui.ListaEventos.placeholder;


/**
 * A placeholder item representing a piece of content.
 */
public class PlaceholderItem {
    public final String id;
    public final String nombre;
    public final String fecha;
    public final boolean evento;//true evento normal || False: EventoMonatana

    public PlaceholderItem(String id, String nombre, String fech, boolean evento) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fech;
        this.evento = evento;
    }

    @Override
    public String toString() {
        return nombre+fecha;
    }
}