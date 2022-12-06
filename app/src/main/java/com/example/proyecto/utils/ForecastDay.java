package com.example.proyecto.utils;

import com.example.proyecto.models.Weather;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class ForecastDay {
    public ForecastDay() {}

    public static boolean forecastNeeded (Date date) {
        LocalDate today = LocalDate.now();
        LocalDate dateEvent = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long days = ChronoUnit.DAYS.between(today, dateEvent);

        return days < 5 && days >= 0;
    }

    public static int getCallDay (Date date) {
        LocalDate today = LocalDate.now();
        LocalDate dateEvent = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long days = ChronoUnit.DAYS.between(today, dateEvent);
        return (int) days;
    }

}
