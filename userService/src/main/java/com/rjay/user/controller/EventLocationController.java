package com.rjay.user.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rjay.user.entity.Event;
import com.rjay.user.entity.EventLocation;
import com.rjay.user.service.EventLocationService;
import com.rjay.user.service.EventService;

@RestController
@RequestMapping("/locations")
public class EventLocationController {

	private final EventLocationService service;

	private final EventService eventService;

	@Autowired
	public EventLocationController(EventLocationService service, EventService eventService) {
		this.service = service;
		this.eventService = eventService;
	}

	@GetMapping("/test")
	public String test() {
		return "your in Location Module";
	}

	@PostMapping("/addLocations")
	public ResponseEntity<EventLocation> createLocation(@RequestBody EventLocation location) {
		System.out.println("Creating location with details: " + location);

		if (location.getEvent() == null || location.getEvent().getId() == null) {
			System.out.println("Event ID is missing in the request");
			return ResponseEntity.badRequest().body(null);
		}

		// Fetch the event by ID and set it to the location
		Optional<Event> eventOptional = eventService.getEventById(location.getEvent().getId());
		if (!eventOptional.isPresent()) {
			System.out.println("Event with ID " + location.getEvent().getId() + " not found");
			return ResponseEntity.badRequest().body(null);
		}

		location.setEvent(eventOptional.get());

		EventLocation createdLocation = service.saveEventLocation(location);
		System.out.println("Created location: " + createdLocation);
		return ResponseEntity.ok(createdLocation);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EventLocation> updateLocation(@PathVariable Long id, @RequestBody EventLocation location) {
	    System.out.println("Updating location with ID: " + id + " with new details: " + location);

	    // Check if location exists
	    Optional<EventLocation> existingLocationOpt = service.getEventLocationById(id);
	    if (!existingLocationOpt.isPresent()) {
	        System.out.println("Location with ID " + id + " not found for update");
	        return ResponseEntity.notFound().build();
	    }

	    // Check if event ID is provided
	    if (location.getEvent() == null || location.getEvent().getId() == null) {
	        System.out.println("Event ID is missing in the request");
	        return ResponseEntity.badRequest().body(null);
	    }

	    // Fetch the event by ID using Optional
	    Optional<Event> eventOpt = eventService.getEventById(location.getEvent().getId());
	    if (!eventOpt.isPresent()) {
	        System.out.println("Event with ID " + location.getEvent().getId() + " not found");
	        return ResponseEntity.badRequest().body(null);
	    }

	    Event event = eventOpt.get();

	    // Update the location
	    EventLocation existingLocation = existingLocationOpt.get();
	    existingLocation.setEvent(event);
	    existingLocation.setStreet(location.getStreet());
	    existingLocation.setCity(location.getCity());
	    existingLocation.setState(location.getState());
	    existingLocation.setPincode(location.getPincode());
	    existingLocation.setLatitude(location.getLatitude());
	    existingLocation.setLongitude(location.getLongitude());

	    // Save the updated location
	    EventLocation updatedLocation = service.saveEventLocation(existingLocation);
	    System.out.println("Updated location: " + updatedLocation);
	    return ResponseEntity.ok(updatedLocation);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<EventLocation>> getAllLocations() {
		try {
			List<EventLocation> locations = service.getAllEventLocations();
			System.out.println("Fetched all locations: " + locations);
			return ResponseEntity.ok(locations);
		} catch (Exception e) {
			System.out.println("Error fetching all locations: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(500).body(null);
		}
	}

	@GetMapping("/getBy/{id}")
	public ResponseEntity<EventLocation> getLocationById(@PathVariable Long id) {
		System.out.println("Fetching location with ID: " + id);
		Optional<EventLocation> location = service.getEventLocationById(id);
		if (location.isPresent()) {
			System.out.println("Fetched location: " + location.get());
			return ResponseEntity.ok(location.get());
		} else {
			System.out.println("Location with ID " + id + " not found");
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteLocation(@PathVariable Long id) {
		System.out.println("Deleting location with ID: " + id);

		if (service.getEventLocationById(id).isPresent()) {
			service.deleteEventLocation(id);
			System.out.println("Deleted location with ID: " + id);

			// Returning 200 OK with a custom message
			return ResponseEntity.ok("Location with ID " + id + " has been successfully deleted");
		} else {
			System.out.println("Location with ID " + id + " not found for deletion");

			// Returning 404 Not Found with a message
			return ResponseEntity.status(404).body("Location with ID " + id + " not found");
		}
	}

	@GetMapping("/geocode")
	public ResponseEntity<String> getGeocodedLocation(@RequestParam Long id) {
		System.out.println("Fetching geocoded address for location with ID: " + id);
		Optional<EventLocation> location = service.getEventLocationById(id);
		if (location.isPresent()) {
			String geocodedAddress = service.getGeocodedAddress(location.get());
			System.out.println("Geocoded address: " + geocodedAddress);
			return ResponseEntity.ok(geocodedAddress);
		} else {
			System.out.println("Location with ID " + id + " not found for geocoding");
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/postal-code")
	public ResponseEntity<List<EventLocation>> findLocationsByPincode(@RequestParam int pincode) {
		System.out.println("Finding locations with pincode: " + pincode);
		List<EventLocation> locations = service.findLocationsByPincode(pincode);
		System.out.println("Found locations: " + locations);
		return ResponseEntity.ok(locations);
	}

	@GetMapping("/nearby")
	public ResponseEntity<List<EventLocation>> findNearbyLocations(@RequestParam Double latitude,
			@RequestParam Double longitude, @RequestParam Double distance) {
		System.out.println("Finding nearby locations for latitude: " + latitude + ", longitude: " + longitude
				+ ", distance: " + distance);
		List<EventLocation> locations = service.findNearbyLocations(latitude, longitude, distance);
		System.out.println("Found nearby locations: " + locations);
		return ResponseEntity.ok(locations);
	}

	@GetMapping("/distance")
	public ResponseEntity<List<EventLocation>> findLocationsWithinDistance(@RequestParam Double latitude,
			@RequestParam Double longitude, @RequestParam Double distance) {
		System.out.println("Finding locations within distance for latitude: " + latitude + ", longitude: " + longitude
				+ ", distance: " + distance);
		List<EventLocation> locations = service.findLocationsWithinDistance(latitude, longitude, distance);
		System.out.println("Found locations within distance: " + locations);
		return ResponseEntity.ok(locations);
	}
}
