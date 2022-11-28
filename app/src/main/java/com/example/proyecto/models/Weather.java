package com.example.proyecto.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Weather {

    public String ciudad;
    public int temperatura;
    public int sensTermica;
    public int tempMinima;
    public int tempMaxima;
    public int presion;
    public int humedad;
    public double velocidadViento;
    public String estadoTiempo;
    public String descEstadoTiempo;
    public int gifResource;

    public Weather() {
    }

    public static Weather fromJson(JSONObject object) throws JSONException {
        Weather weather = new Weather();
        weather.ciudad = object.getString("name");
        weather.temperatura = (int) Math.round(object.getJSONObject("main").getDouble("temp") - 273.15);
        weather.sensTermica = (int) Math.round(object.getJSONObject("main").getDouble("feels_like") - 273.15);
        weather.tempMinima = (int) Math.round(object.getJSONObject("main").getDouble("temp_min") - 273.15);
        weather.tempMaxima = (int) Math.round(object.getJSONObject("main").getDouble("temp_max") - 273.15);
        weather.presion = object.getJSONObject("main").getInt("pressure");
        weather.humedad = object.getJSONObject("main").getInt("humidity");
        weather.velocidadViento = object.getJSONObject("wind").getDouble("speed");
        weather.estadoTiempo = object.getJSONArray("weather").getJSONObject(0).getString("main");
        weather.descEstadoTiempo = object.getJSONArray("weather").getJSONObject(0).getString("description");
        weather.gifResource = getGifResource(object.getJSONArray("weather").getJSONObject(0).getInt("id"));

        return weather;
    }

    public static Weather fromJson(JSONObject object, int dia) throws JSONException {
        int index = dia * 8;

        Weather weather = new Weather();
        weather.ciudad = object.getJSONObject("city").getString("name");
        weather.temperatura = (int) Math.round(object.getJSONArray("list").getJSONObject(index).getJSONObject("main").getDouble("temp") - 273.15);
        weather.sensTermica = (int) Math.round(object.getJSONArray("list").getJSONObject(index).getJSONObject("main").getDouble("feels_like") - 273.15);
        weather.tempMinima = (int) Math.round(object.getJSONArray("list").getJSONObject(index).getJSONObject("main").getDouble("temp_min") - 273.15);
        weather.tempMaxima = (int) Math.round(object.getJSONArray("list").getJSONObject(index).getJSONObject("main").getDouble("temp_max") - 273.15);
        weather.presion = object.getJSONArray("list").getJSONObject(index).getJSONObject("main").getInt("pressure");
        weather.humedad = object.getJSONArray("list").getJSONObject(index).getJSONObject("main").getInt("humidity");
        weather.velocidadViento = object.getJSONArray("list").getJSONObject(index).getJSONObject("wind").getDouble("speed");
        weather.estadoTiempo = object.getJSONArray("list").getJSONObject(index).getJSONArray("weather").getJSONObject(0).getString("main");
        weather.descEstadoTiempo = object.getJSONArray("list").getJSONObject(index).getJSONArray("weather").getJSONObject(0).getString("description");
        weather.gifResource = getGifResource(object.getJSONArray("list").getJSONObject(index).getJSONArray("weather").getJSONObject(0).getInt("id"));

        return weather;
    }

    /**
     *
     * @param  condition
     *         0 Nada
     *         1 Tormenta
     *         2 Llovizna
     *         3 Lluvia
     *         4 Nieve
     *         5 Niebla
     *         6 Nubes
     *         7 Sol
     */
    public static int getGifResource(int condition) {
        if (condition >= 0 && condition < 300) { // Tormenta
            return 1;
        } else if (condition >= 300 && condition < 500) { // Llovizna
            return 2;
        } else if (condition >= 500 && condition < 600) { // Lluvia
            return 3;
        } else if (condition >= 600 && condition <= 700) { // Nieve
            return 4;
        } else if (condition >= 701 && condition <= 771) { // Niebla
            return 5;
        } else if (condition >= 801 && condition <= 804) { // Nubes
            return 6;
        } else if (condition == 800) {                     // Sol
            return 7;
        }
        return 0;
    }

    //Método para obtener todas las palabras con la primera letra en Mayusculas
    public String getEstadoTiempoMay (){
        char[] charArray = estadoTiempo.toCharArray();
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

    //Método para obtener todas las palabras con la primera letra en Mayusculas
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
