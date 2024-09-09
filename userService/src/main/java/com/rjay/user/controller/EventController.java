package com.rjay.user.controller;

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

import com.rjay.user.dto.CommonResponseAPI;
import com.rjay.user.dto.EventAddRequest;
import com.rjay.user.dto.EventResponseDTO;
import com.rjay.user.dto.EventUpdateRequest;
import com.rjay.user.resource.EventResource;

@RestController
@RequestMapping("/event")
public class EventController {

	@Autowired
	private EventResource eventResource;

	@GetMapping("/test")
	public String testapi() {
		return "event started";
	}

	@PostMapping("/addevent")
	public ResponseEntity<CommonResponseAPI> addEvent(@RequestBody EventAddRequest eventDto) {
		return this.eventResource.addEvent(eventDto);
	}

	@PutMapping("/updateEventDetails")
	public ResponseEntity<CommonResponseAPI> updateEventDetails(@RequestBody EventUpdateRequest request) {
		return this.eventResource.updateEventDetail(request);
	}

	@GetMapping("/fetchEventById")
	public ResponseEntity<EventResponseDTO> fetchEventById(@RequestParam(name = "eventId") int eventId) {
		return this.eventResource.fetchEventById(eventId);
	}

	@GetMapping("/fetchAllEvents")
	public ResponseEntity<EventResponseDTO> fetchAllEvent() {
		return this.eventResource.fetchAllEvent();
	}
	
	@GetMapping("/searchEvent")
	public ResponseEntity<EventResponseDTO> searchProductsByName(
			@RequestParam(name = "productName") String productName) {
		return this.eventResource.searchEventByName(productName);
	}
	
	@GetMapping("/fetchEvent/category-wise")
	public ResponseEntity<EventResponseDTO> fetchAllEventsByCategory(
			@RequestParam(name = "categoryId") int categoryId) {
		return this.eventResource.fetchAllEventsByCategory(categoryId);
	}
	
	@GetMapping("/fetchEventsByManagerId/{managerId}")
	public ResponseEntity<EventResponseDTO> fetchEventsByManagerId(@PathVariable(name = "managerId") Long managerId) {
	    return this.eventResource.fetchAllEventsByManagerId(managerId);
	}
	
	@DeleteMapping("/deleteEvent/{eventId}")
    public ResponseEntity<CommonResponseAPI> deleteEventById(@PathVariable("eventId") long eventId) {
        return this.eventResource.deleteEventById(eventId);
    }

	
}