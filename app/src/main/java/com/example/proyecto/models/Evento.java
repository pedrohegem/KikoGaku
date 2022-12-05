package com.example.proyecto.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.proyecto.utils.DateConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Evento implements Comparable<Evento> {

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

    // Atributos referidos al tiempo del evento
    private int temperatura;
    private int sensTermica;
    private int tempMinima;
    private int tempMaxima;
    private int presion;
    private int humedad;
    private double velocidadViento;
    private String estadoTiempo;
    private String descEstadoTiempo;
    private int gifResource;

    @TypeConverters(DateConverter.class)
    private Date fecha;

    public Evento(String titulo, String ubicacion, String descripcion, Date fecha, boolean esMunicipio) {
        this.titulo = titulo;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.esMunicipio = esMunicipio;
        this.temperatura = temperatura;
        this.sensTermica = sensTermica;
        this.tempMinima = tempMinima;
        this.tempMaxima = tempMaxima;
        this.presion = presion;
        this.humedad = humedad;
        this.velocidadViento = velocidadViento;
        this.estadoTiempo = estadoTiempo;
        this.descEstadoTiempo = descEstadoTiempo;
        this.gifResource = gifResource;
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
        this.temperatura = 0;
        this.sensTermica = 0;
        this.tempMinima = 0;
        this.tempMaxima = 0;
        this.presion = 0;
        this.humedad = 0;
        this.velocidadViento = 0.0;
        this.estadoTiempo = "";
        this.descEstadoTiempo = "";
        this.gifResource = -1;
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

    public void setWeather(Weather weather) {
        this.temperatura = weather.temperatura;
        this.sensTermica = weather.sensTermica;
        this.tempMinima = weather.tempMinima;
        this.tempMaxima = weather.tempMaxima;
        this.presion = weather.presion;
        this.humedad = weather.humedad;
        this.velocidadViento = weather.velocidadViento;
        this.estadoTiempo = weather.estadoTiempo;
        this.descEstadoTiempo = weather.descEstadoTiempo;
        this.gifResource = weather.gifResource;
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


    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public int getSensTermica() {
        return sensTermica;
    }

    public void setSensTermica(int sensTermica) {
        this.sensTermica = sensTermica;
    }

    public int getTempMinima() {
        return tempMinima;
    }

    public void setTempMinima(int tempMinima) {
        this.tempMinima = tempMinima;
    }

    public int getTempMaxima() {
        return tempMaxima;
    }

    public void setTempMaxima(int tempMaxima) {
        this.tempMaxima = tempMaxima;
    }

    public int getPresion() {
        return presion;
    }

    public void setPresion(int presion) {
        this.presion = presion;
    }

    public int getHumedad() {
        return humedad;
    }

    public void setHumedad(int humedad) {
        this.humedad = humedad;
    }

    public double getVelocidadViento() {
        return velocidadViento;
    }

    public void setVelocidadViento(double velocidadViento) {
        this.velocidadViento = velocidadViento;
    }

    public String getEstadoTiempo() {
        return estadoTiempo;
    }

    public void setEstadoTiempo(String estadoTiempo) {
        this.estadoTiempo = estadoTiempo;
    }

    public String getDescEstadoTiempo() {
        return descEstadoTiempo;
    }

    public void setDescEstadoTiempo(String descEstadoTiempo) {
        this.descEstadoTiempo = descEstadoTiempo;
    }

    public int getGifResource() {
        return gifResource;
    }

    public void setGifResource(int gifResource) {
        this.gifResource = gifResource;
    }
}
