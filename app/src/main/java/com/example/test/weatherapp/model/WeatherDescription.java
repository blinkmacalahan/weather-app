package com.example.test.weatherapp.model;

public class WeatherDescription {
    public final int id;
    public final String condition;
    public final String description;
    public String icon;

    public WeatherDescription(int id, String condition, String description,
            String icon) {
        this.id = id;
        this.condition = condition;
        this.description = description;
    }
}
