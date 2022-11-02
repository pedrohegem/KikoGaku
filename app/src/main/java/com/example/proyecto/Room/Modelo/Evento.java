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
import java.util.Locale;

@Entity
public class Evento {

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

    // Campos que no forman parte de la tabla
    @Ignore
    private int probPrecipitacion;
    @Ignore
    private String estadoCielo;
    @Ignore
    private int velocidadViento;
    @Ignore
    private int tempMax;
    @Ignore
    private int tempMin;
    @Ignore
    private int sensacionTerminaMax;
    @Ignore
    private int getSensacionTerminaMin;

    public Evento(String titulo, String ubicacionCode, String descripcion, Date fecha) {
        this.titulo = titulo;
        this.ubicacionCode = ubicacionCode;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }
    @Ignore
    public Evento(int ide, String titulo, String ubicacionCode, String descripcion, String fecha, int probPrecipitacion, String estadoCielo, int velocidadViento, int tempMax, int tempMin, int sensacionTerminaMax, int getSensacionTerminaMin) {
        this.ide = ide;
        this.titulo = titulo;
        this.ubicacionCode = ubicacionCode;
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
