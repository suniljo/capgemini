package com.capgemini.caching.services;

import java.util.Optional;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.capgemini.caching.entities.Weather;
import com.capgemini.caching.repository.WeatherRepository;

@Service
public class WeatherServices {

	private final WeatherRepository weatherRepository;

	public WeatherServices(WeatherRepository weatherRepository) {
		this.weatherRepository = weatherRepository;
	}

	@Cacheable(value = "weather", key = "#city")
	public String getWeatherByCity(String city) {
		System.out.println("Fetching data from DB for city: " + city);
		Optional<Weather> weather = weatherRepository.findByCity(city);
		return weather.map(Weather::getForecast).orElse("Weather data not available");
	}
	
	//@CachePut(value = "weather", key = "#city")
	public String updateWeather(String city, String updatedWeather) {
		weatherRepository.findByCity(city).ifPresent(weather -> {
			weather.setForecast(updatedWeather);
			weatherRepository.save(weather);
		});
		return updatedWeather;
	}	
}