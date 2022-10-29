package com.example.proyecto.Room;

public class PicoMontaña {

    private int nombreMontaña;
    private int tempMax;
    private int tempMin;
    private int sensacionTerminaMax;
    private int getSensacionTerminaMin;

    public PicoMontaña(){

    }

    public PicoMontaña(int nombreMontaña, int tempMax, int tempMin, int sensacionTerminaMax, int getSensacionTerminaMin) {
        this.nombreMontaña = nombreMontaña;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.sensacionTerminaMax = sensacionTerminaMax;
        this.getSensacionTerminaMin = getSensacionTerminaMin;
    }

    public int getNombreMontaña() {
        return nombreMontaña;
    }

    public void setNombreMontaña(int nombreMontaña) {
        this.nombreMontaña = nombreMontaña;
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
