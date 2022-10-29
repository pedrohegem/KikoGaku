package com.example.proyecto.Room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import androidx.annotation.NonNull;

import java.util.Date;

@Entity
public class EventoEntity {

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

    //@ColumnInfo
    //private String fecha;

    private int probPrecipitacion;

    private String estadoCielo;

    private int codCielo;

    private int velocidadViento;

    private int tempMax;
    private int tempMin;

    private int sensacionTerminaMax;
    private int getSensacionTerminaMin;

    public EventoEntity(){

    }

    public EventoEntity(int ide, String titulo, String ubicacion, String descripcion, Date fecha, int probPrecipitacion, String estadoCielo, int codCielo, int velocidadViento, int tempMax, int tempMin, int sensacionTerminaMax, int getSensacionTerminaMin) {
        this.ide = ide;
        this.titulo = titulo;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        //this.fecha = fecha;
        this.probPrecipitacion = probPrecipitacion;
        this.estadoCielo = estadoCielo;
        this.codCielo = codCielo;
        this.velocidadViento = velocidadViento;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.sensacionTerminaMax = sensacionTerminaMax;
        this.getSensacionTerminaMin = getSensacionTerminaMin;
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
/*
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
*/
    public int getProbPrecipitacion() {
        return probPrecipitacion;
    }

    public void setProbPrecipitacion(int probPrecipitacion) {
        this.probPrecipitacion = probPrecipitacion;
    }

    public String getEstadoCielo() {
        return estadoCielo;
    }

    public void setEstadoCielo(String estadoCielo) {
        this.estadoCielo = estadoCielo;
    }

    public int getCodCielo() {
        return codCielo;
    }

    public void setCodCielo(int codCielo) {
        this.codCielo = codCielo;
    }

    public int getVelocidadViento() {
        return velocidadViento;
    }

    public void setVelocidadViento(int velocidadViento) {
        this.velocidadViento = velocidadViento;
    }

    public int getTempMax() {
        return tempMax;
    }

    public void setTempMax(int tempMax) {
        this.tempMax = tempMax;
    }

    public int getTempMin() {
        return tempMin;
    }

    public void setTempMin(int tempMin) {
        this.tempMin = tempMin;
    }

    public int getSensacionTerminaMax() {
        return sensacionTerminaMax;
    }

    public void setSensacionTerminaMax(int sensacionTerminaMax) {
        this.sensacionTerminaMax = sensacionTerminaMax;
    }

    public int getGetSensacionTerminaMin() {
        return getSensacionTerminaMin;
    }

    public void setGetSensacionTerminaMin(int getSensacionTerminaMin) {
        this.getSensacionTerminaMin = getSensacionTerminaMin;
    }
}
