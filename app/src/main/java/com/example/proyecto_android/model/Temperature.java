package com.example.proyecto_android.model;

import com.google.gson.annotations.SerializedName;

public class Temperature {

    @SerializedName("temp")
    private float temperature;
    private float pressure;
    private float humidity;
    @SerializedName("temp_max")
    private float tempMax;
    @SerializedName("tem_min")
    private float tempMin;
    @SerializedName("feels_like")
    private float feelsLike;
    @SerializedName("description")
    private String description;

    public Temperature() {
    }

    public Temperature(float temperature, float pressure, float humidity, float tempMax, float tempMin, float feelsLike, String description) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.feelsLike = feelsLike;
        this.description = description;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    public float getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(float feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
