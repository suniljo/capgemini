package com.capgemini.caching.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.caching.entities.Weather;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

	Optional<Weather> findByCity(String city);

}
