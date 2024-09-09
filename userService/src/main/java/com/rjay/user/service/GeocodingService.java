package com.rjay.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingService {

    @Value("${geocoding.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getGeocodedLocation(String address) {
        String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s",
                                   address, apiKey);
        String response = restTemplate.getForObject(url, String.class);
        return response;
    }
}
