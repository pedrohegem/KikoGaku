package com.example.proyecto.Room.Modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.proyecto.Room.javadb.DateConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
public class EventoMontana {

    @Ignore
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.US);

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int ide;

    private String titulo;

    private String ubicacionCode;

    private String descripcion;

    @TypeConverters(DateConverter.class)
    private Date fecha;

    @Ignore
    private List<PicoMontana> picosLista;

    public EventoMontana(String titulo, String ubicacionCode, String descripcion, Date fecha) {
        this.titulo = titulo;
        this.ubicacionCode = ubicacionCode;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }
    @Ignore
    public EventoMontana(int ide, String titulo, String ubicacionCode, String descripcion, String fecha, List<PicoMontana> picosLista) {
        this.ide = ide;
        this.titulo = titulo;
        this.ubicacionCode = ubicacionCode;
        this.descripcion = descripcion;
        try {
            this.fecha = Evento.FORMAT.parse(fecha);
        } catch (ParseException e) {
            this.fecha = new Date();
        }
        this.picosLista = picosLista;
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

    public String getUbicacionCode() {
        return ubicacionCode;
    }

    public void setUbicacionCode(String ubicacionCode) {
        this.ubicacionCode = ubicacionCode;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<PicoMontana> getPicosLista() {
        return picosLista;
    }

    public void setPicosLista(List<PicoMontana> picosLista) {
        this.picosLista = picosLista;
    }
}
