package com.example.proyecto.Room.Modelo;

public class PicoMontana {
    private int nombreMontana;
    private int tempMax;
    private int tempMin;
    private int sensacionTerminaMax;
    private int getSensacionTerminaMin;

    public PicoMontana(){

    }

    public PicoMontana(int nombreMontana, int tempMax, int tempMin, int sensacionTerminaMax, int getSensacionTerminaMin) {
        this.nombreMontana = nombreMontana;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.sensacionTerminaMax = sensacionTerminaMax;
        this.getSensacionTerminaMin = getSensacionTerminaMin;
    }

    public int getNombreMontana() {
        return nombreMontana;
    }

    public void setNombreMontana(int nombreMontana) {
        this.nombreMontana = nombreMontana;
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
