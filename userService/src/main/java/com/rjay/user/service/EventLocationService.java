package com.rjay.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rjay.user.entity.EventLocation;
import com.rjay.user.repository.EventLocationRepository;
import com.rjay.user.utility.DistanceCalculator;



@Service
public class EventLocationService {

    private final EventLocationRepository repository;
    private final GeocodingService geocodingService;

    @Autowired
    public EventLocationService(EventLocationRepository repository, GeocodingService geocodingService) {
        this.repository = repository;
        this.geocodingService = geocodingService;
    }

    public EventLocation saveEventLocation(EventLocation eventLocation) {
        return repository.save(eventLocation);
    }

    public List<EventLocation> getAllEventLocations() {
        return repository.findAll();
    }

    public Optional<EventLocation> getEventLocationById(Long id) {
        return repository.findById(id);
    }

    public void deleteEventLocation(Long id) {
        repository.deleteById(id);
    }

    public String getGeocodedAddress(EventLocation eventLocation) {
        String address = String.format("%s, %s, %s, %s",
                                       eventLocation.getStreet(),
                                       eventLocation.getCity(),
                                       eventLocation.getState(),
                                       eventLocation.getPincode());
        return geocodingService.getGeocodedLocation(address);
    }

    public List<EventLocation> findLocationsByPincode(int pincode) {
        return repository.findByPincode(pincode);
    }
    
    public List<EventLocation> findNearbyLocations(Double latitude, Double longitude, Double distance) {
        // Example logic for calculating distance
        // You would need a real distance calculation method here
        double earthRadius = 6371; // Radius in kilometers

        return repository.findAll().stream()
                .filter(location -> {
                    double dLat = Math.toRadians(location.getLatitude() - latitude);
                    double dLon = Math.toRadians(location.getLongitude() - longitude);
                    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                               Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(location.getLatitude())) *
                               Math.sin(dLon / 2) * Math.sin(dLon / 2);
                    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                    double distanceCalc = earthRadius * c;
                    return distanceCalc <= distance;
                })
                .collect(Collectors.toList());
    }
    
    public List<EventLocation> findLocationsWithinDistance(Double latitude, Double longitude, Double distance) {
        return repository.findAll().stream()
                .filter(location -> {
                    double locationLat = location.getLatitude();
                    double locationLon = location.getLongitude();
                    double calculatedDistance = DistanceCalculator.calculateDistance(latitude, longitude, locationLat, locationLon);
                    return calculatedDistance <= distance;
                })
                .collect(Collectors.toList());
    }
}