package com.example.test.weatherapp.ui;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.weatherapp.R;
import com.example.test.weatherapp.api.OpenWeatherService;
import com.example.test.weatherapp.model.Forecast;
import com.example.test.weatherapp.model.ForecastResponse;
import com.example.test.weatherapp.model.WeatherDescription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FiveDayForecastFragment extends Fragment {
    private static final String TAG = FiveDayForecastFragment.class.getSimpleName();
    private OpenWeatherService mOpenWeatherService;

    private ViewGroup mForecastHolder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Resources res = getResources();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();
        Retrofit openWeatherRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(res.getString(R.string.open_weather_api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mOpenWeatherService = openWeatherRetrofit
                .create(OpenWeatherService.class);
        new FiveDayForecast().execute("85018");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_five_day_forecast, container, false);

        mForecastHolder = root.findViewById(R.id.five_day_forecast);
        return root;
    }

    @SuppressLint("StaticFieldLeak")
    private class FiveDayForecast extends AsyncTask<String, Void, List<Forecast>> {
        @Override
        protected List<Forecast> doInBackground(String... strings) {
            final String zipcode = strings[0];
            Call<ForecastResponse> fiveDayForecast = mOpenWeatherService
                    .get5DayForecast(zipcode, getResources()
                            .getString(R.string.open_weather_api_key));

            List<Forecast> forecasts = new ArrayList<>();
            try {

                Response<ForecastResponse> response = fiveDayForecast.execute();
                if (response.isSuccessful() && response.body() != null) {
                    ForecastResponse resp = response.body();
                    for (ForecastResponse.ForecastScratch item : resp.forecasts) {
                        ForecastResponse.WeatherData weatherData = item.weather
                                .get(0);
                        Forecast forecast = new Forecast(item.main.temp,
                                item.main.temp_max,
                                item.main.temp_min,
                                item.main.humidity,
                                item.dt,
                                new WeatherDescription(weatherData.id,
                                        weatherData.main,
                                        weatherData.description,
                                        weatherData.icon));
                        forecasts.add(forecast);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return forecasts;
        }

        @Override
        protected void onPostExecute(List<Forecast> forecasts) {
            super.onPostExecute(forecasts);
            Log.d(TAG, forecasts.toString());
            updateForecast(forecasts);
        }
    }

    private void updateForecast(List<Forecast> forecasts) {
        Resources res = getResources();
        for (int i = 0; i < forecasts.size(); i++) {
            // Only grab the first 5 days
            if (i == 5) return;
            // Skip over <Space/> views to get to the TextView children.
            TextView child = (TextView) mForecastHolder.getChildAt((i*2) + 1);
            child.setText(res.getString(R.string.low_hi_temps, forecasts.get(i).minTemp, forecasts.get(i).maxTemp));
        }
    }
}
