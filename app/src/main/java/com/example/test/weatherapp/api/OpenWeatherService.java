package com.example.test.weatherapp.api;

import com.example.test.weatherapp.model.ForecastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Example http request: https://api.openweathermap.org/data/2.5/forecast?zip=94040&units=imperial&appid=8df9894bcace71d40e078411678d7fe8
 */
public interface OpenWeatherService {
    @GET("forecast?units=imperial")
    Call<ForecastResponse> get5DayForecast(@Query("zip") String zipcode,
            @Query("appid") String apiKey);
}
