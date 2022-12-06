package com.example.proyecto.repository.networking;

import android.util.Log;

import com.example.proyecto.models.Weather;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class APIManager {

    // Interfaz que maneja la delegación de los resultados de las llamadas a la API
    public APIManagerDelegate delegate = null;

    private final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    private final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast";

    // TODO: Ocultar key en local.properties, añadiendola en metadatos del manifest y añadir plugin com.google.secrets_gradle_plugin a build.gradle...etc
    private final String API_KEY = "3c37b8776fd8ba64116ac542be924ff1";

    public APIManager(APIManagerDelegate delegate) {
        this.delegate = delegate;
    }

    // Método sobrecargado que parametriza la llamada al tiempo actual en base a la CIUDAD
    public void getEventWeather(String city) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("q", city);
        requestParams.put("appid", API_KEY);
        requestParams.put("lang", "es");
        weatherNetworking(requestParams);
    }

    // Método sobrecargado que parametriza la llamada al tiempo actual en base a la LATITUD y LONGITUD
    public void getEventWeather(double lat, double lon) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("lat", lat);
        requestParams.put("lon", lon);
        requestParams.put("appid", API_KEY);
        requestParams.put("lang", "es");
        weatherNetworking(requestParams);
    }

    // Método que parametriza la llamada a la PREDICCIÓN del tiempo de X día en base a la CIUDAD
    // X será un día de 0 (actual) a 4 de los 5 que devuelve la predicción de la API.
    public void getEventForecast(String city, int dia) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("q", city);
        requestParams.put("appid", API_KEY);
        requestParams.put("lang", "es");
        forecastNetworking(requestParams, dia);
    }

    // Método que parametriza la llamada a la PREDICCIÓN del tiempo de X día en base a la LATITUD y LONGITUD
    // X será un día de 0 (actual) a 4 de los 5 que devuelve la predicción de la API.
    public void getEventForecast(double lat, double lon, int dia) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("lat", lat);
        requestParams.put("lon", lon);
        requestParams.put("appid", API_KEY);
        requestParams.put("lang", "es");
        forecastNetworking(requestParams, dia);
    }

    public void weatherNetworking(RequestParams requestParams) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess (int statusCode, Header[] headers, JSONObject response) {
                Log.d("WeatherCall", "onSuccess: " + response.toString());
                try {
                    delegate.onGetWeatherSuccess(Weather.fromJson(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.e("WeatherCall", "onFailure: " + e.toString());
                delegate.onGetWeatherFailure();
            }

            @Override
            public boolean getUseSynchronousMode() {
                return false;
            }
        });
    }

    public void forecastNetworking(RequestParams requestParams, int dia) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(FORECAST_URL, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess (int statusCode, Header[] headers, JSONObject response) {
                Log.d("ForecastCall", "onSuccess: " + response.toString());
                try {
                    delegate.onGetWeatherSuccess(Weather.fromJson(response, dia));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.e("ForecastCall", "onFailure: " + e.toString());
                delegate.onGetWeatherFailure();
            }

            @Override
            public boolean getUseSynchronousMode() {
                return false;
            }
        });
    }
}
