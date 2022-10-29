package com.example.proyecto.Room.Modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
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

    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.US);

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    @NonNull
    private int ide;

    @ColumnInfo
    private String titulo;

    @ColumnInfo
    private String ubicacionCode;

    @ColumnInfo
    private String descripcion;

    @ColumnInfo
    @TypeConverters(DateConverter.class)
    private Date fecha;

    private List<PicoMontana> picosLista;

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
