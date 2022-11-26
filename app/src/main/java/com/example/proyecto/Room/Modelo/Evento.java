package com.example.proyecto.Room.Modelo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.proyecto.Room.javadb.DateConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

@Entity
public class Evento implements Comparable<Evento> {

    public static final String IDE="ide";
    public static final String TÍTULO="titulo";
    public static final String UBICACION="ubicacion";
    public static final String DESCRIPCION="descripcion";
    public static final String ESMUNICIPIO="esMunicipio";
    public static final String FECHA="fecha";

    @Ignore
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.US);

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int ide;

    private String titulo;

    private String ubicacion;

    private String descripcion;

    private boolean esMunicipio;

    @TypeConverters(DateConverter.class)
    private Date fecha;

    public Evento(String titulo, String ubicacion, String descripcion, Date fecha, boolean esMunicipio) {
        this.titulo = titulo;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;

        this.fecha = fecha;
        this.esMunicipio = esMunicipio;
    }
    @Ignore
    public Evento(String titulo, String ubicacion, String descripcion, String fecha, boolean esMunicipio) {
        this.titulo = titulo;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        try {
            this.fecha = Evento.FORMAT.parse(fecha);
        } catch (ParseException e) {
            this.fecha = new Date();
        }
        this.esMunicipio = esMunicipio;
    }

    @Ignore
    public Evento() {

    }

    public int getIde() {
        return ide;
    }

    public void setIde(int ide) {
        this.ide = ide;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getEsMunicipio(){
        return esMunicipio;
    }

    public void setEsMunicipio(boolean esMunicipio){
        this.esMunicipio = esMunicipio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


    @Override
    public int compareTo(Evento o) {
        if (o.getFecha().compareTo(fecha)<0){
            return 1;
        }else{
            if (o.getFecha().compareTo(fecha)>0){
                return -1;
            }
        }
        return 0;
    }
}
