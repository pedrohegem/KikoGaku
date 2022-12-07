package com.example.proyecto.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Location {
    @PrimaryKey
    @NonNull
    private String ubicacion;
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

    public Location(String ubicacion) {
        this.ubicacion = ubicacion;
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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
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

    public String getDescEstadoTiempoMay (){
        char[] charArray = descEstadoTiempo.toCharArray();
        boolean foundSpace = true;

        for(int i = 0; i < charArray.length; i++) {

            // if the array element is a letter
            if (Character.isLetter(charArray[i])) {

                // check space is present before the letter
                if (foundSpace) {

                    // change the letter into uppercase
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            } else {
                // if the new character is not character
                foundSpace = true;
            }
        }
        return String.valueOf(charArray);
    }
}
