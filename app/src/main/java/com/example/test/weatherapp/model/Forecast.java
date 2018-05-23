package com.example.test.weatherapp.model;

public class Forecast {
    public final int currentTemp;
    public final int maxTemp;
    public final int minTemp;
    public final int humidity;

    public final long datetime;
    public final WeatherDescription weatherDesc;

    public Forecast(float currentTemp, float maxTemp, float minTemp,
            int humidity, long datetime, WeatherDescription weatherDesc) {
        this.currentTemp = (int) currentTemp;
        this.maxTemp = (int) maxTemp;
        this.minTemp = (int) minTemp;
        this.humidity = humidity;
        this.datetime = datetime;
        this.weatherDesc = weatherDesc;
    }
}
