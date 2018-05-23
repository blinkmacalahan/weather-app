package com.example.test.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastResponse {
    @SerializedName("list")
    public List<ForecastScratch> forecasts;

    public static class ForecastScratch {
        public long dt;
        public MainForecastData main;
        public List<WeatherData> weather;
        public WindData wind;
    }

    public static class MainForecastData {
        public float temp;
        public float temp_min;
        public float temp_max;
        public int humidity;
    }

    public static class WeatherData {
        public int id;
        public String main;
        public String description;
        public String icon;
    }

    public static class WindData {
        public float speed;
    }
}
