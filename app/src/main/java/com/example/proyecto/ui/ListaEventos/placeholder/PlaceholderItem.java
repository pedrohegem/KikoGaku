package com.example.proyecto.ui.ListaEventos.placeholder;


/**
 * A placeholder item representing a piece of content.
 */
public class PlaceholderItem {
    public final int ide;
    public final String id;
    public final String nombre;
    public final String fecha;
    public final String localizacion;
    public final boolean evento;//true evento normal || False: EventoMonatana

    public PlaceholderItem(int ide, String id, String nombre, String fech, String localizacion, boolean evento) {
        this.ide = ide;
        this.id = id;
        this.nombre = nombre;
        this.fecha = fech;
        this.localizacion = localizacion;
        this.evento = evento;
    }

    @Override
    public String toString() {
        return nombre+fecha;
    }
}