package com.example.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Service
public class WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.url}")
    private String apiUrl;

    @SuppressWarnings("unchecked")
    public Map<String, Object> getWeather(String city) {
        URI uri = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("q", city)
                .queryParam("units", "metric")
                .queryParam("appid", apiKey)
                .build()
                .encode()
                .toUri();
        return restTemplate.getForObject(uri, Map.class);
    }
}
