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
import java.util.Locale;

@Entity
public class Evento {

    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.US);

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    @NonNull
    private int ide;

    @ColumnInfo
    private String titulo;

    @ColumnInfo
    private int ubicacionCode;

    @ColumnInfo
    private String descripcion;

    @ColumnInfo
    @TypeConverters(DateConverter.class)
    private Date fecha;

    // Campos que no forman parte de la tabla

    private int probPrecipitacion;

    private String estadoCielo;

    private int velocidadViento;

    private int tempMax;
    private int tempMin;

    private int sensacionTerminaMax;
    private int getSensacionTerminaMin;


    public Evento(int ide, String titulo, int ubicacion, String descripcion, String fecha, int probPrecipitacion, String estadoCielo, int velocidadViento, int tempMax, int tempMin, int sensacionTerminaMax, int getSensacionTerminaMin) {
        this.ide = ide;
        this.titulo = titulo;
        this.ubicacionCode = ubicacion;
        this.descripcion = descripcion;
        this.probPrecipitacion = probPrecipitacion;
        this.estadoCielo = estadoCielo;
        this.velocidadViento = velocidadViento;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.sensacionTerminaMax = sensacionTerminaMax;
        this.getSensacionTerminaMin = getSensacionTerminaMin;

        try {
            this.fecha = Evento.FORMAT.parse(fecha);
        } catch (ParseException e) {
            this.fecha = new Date();
        }

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

    public int getUbicacionCode() {
        return ubicacionCode;
    }

    public void setUbicacionCode(int ubicacionCode) {
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
