package com.capgemini.caching.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.caching.entities.Weather;
import com.capgemini.caching.repository.WeatherRepository;
import com.capgemini.caching.services.CacheInspectionService;
import com.capgemini.caching.services.WeatherServices;

@RestController
@RequestMapping(path="/api/weather")
public class WeatherRestController {
	@Autowired
	private WeatherRepository weatherRepo;
	
	@Autowired
	private WeatherServices weatherServices;
	
    @Autowired
    private CacheInspectionService cacheInspectionService;

	@PostMapping
	public Weather addWeather(@RequestBody Weather weather) {
		return weatherRepo.save(weather);
	}	
	
	@GetMapping
	public String getWeather(@RequestParam String city) {
		String weatherByCity = weatherServices.getWeatherByCity(city);
		return weatherByCity;
	}
	
    @GetMapping("/cacheData")
    public void getCacheDate() {
        cacheInspectionService.printCacheContents("weather");
    }	
    
    @PutMapping("/{city}")
    public String updateWeather(@PathVariable String city, @RequestParam String weatherUpdate) {
        return weatherServices.updateWeather(city, weatherUpdate);
    }    
}
