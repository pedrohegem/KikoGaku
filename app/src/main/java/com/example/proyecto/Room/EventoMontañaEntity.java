package com.example.proyecto.Room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.List;

public class EventoMontañaEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    @NonNull
    private int ide;

    @ColumnInfo
    private String titulo;

    @ColumnInfo
    private String ubicacion;

    @ColumnInfo
    private String descripcion;

    @ColumnInfo
    private Date fecha;

    private List<PicoMontaña> picosLista;

    public EventoMontañaEntity() {
    }

    public EventoMontañaEntity(int ide, String titulo, String ubicacion, String descripcion, Date fecha, List<PicoMontaña> picosLista) {
        this.ide = ide;
        this.titulo = titulo;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.fecha = fecha;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<PicoMontaña> getPicosLista() {
        return picosLista;
    }

    public void setPicosLista(List<PicoMontaña> picosLista) {
        this.picosLista = picosLista;
    }
}
