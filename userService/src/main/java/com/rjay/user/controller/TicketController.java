package com.rjay.user.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rjay.user.dto.EventResponseDTO;
import com.rjay.user.dto.TicketDto;
import com.rjay.user.dto.UserProfileDTO;
import com.rjay.user.entity.Event;
import com.rjay.user.entity.Ticket;
import com.rjay.user.entity.User;
import com.rjay.user.resource.EventResource;
import com.rjay.user.resource.UserResource;
import com.rjay.user.service.EventService;
import com.rjay.user.service.TicketService;
import com.rjay.user.service.UserService;


@RestController
@RequestMapping("/ticket")
public class TicketController {

	@Autowired
	private final TicketService tservice;

	@Autowired
	private final UserService userService;
	private final EventService eventService;
	private final EventResource eventResource;
	private final UserResource userResource;

	@Autowired
	public TicketController(UserService userService, EventService eventService, TicketService tservice,
			EventResource eventResource,UserResource userResource) {
		this.userService = userService;
		this.eventService = eventService;
		this.tservice = tservice;
		this.eventResource=eventResource;
		this.userResource=userResource;
	}
	
	@GetMapping("/test")
	public String test() {
		return "your in Ticket Module";
	}

	@PostMapping("/book")
	public ResponseEntity<String> bookTicket(@RequestBody TicketDto ticketDto) {
	    // Fetch user profile using Feign client
	    String emailId = ticketDto.getEmailId();
	    ResponseEntity<UserProfileDTO> userProfileResponse = userResource.getUserProfile(emailId);

	    if (!userProfileResponse.getStatusCode().is2xxSuccessful()) {
	        return ResponseEntity.status(userProfileResponse.getStatusCode()).body("User not found");
	    }

	    // Fetch all events using Feign client
	    ResponseEntity<EventResponseDTO> eventsResponse = eventResource.fetchAllEvent();
	    if (!eventsResponse.getStatusCode().is2xxSuccessful()) {
	        return ResponseEntity.status(eventsResponse.getStatusCode()).body("Events could not be fetched");
	    }

	    // Check if the selected event exists
	    EventResponseDTO eventResponseDTO = eventsResponse.getBody();
	    if (eventResponseDTO == null || eventResponseDTO.getEvent() == null) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No events found");
	    }

	    // Find the selected event
	    Event selectedEvent = eventResponseDTO.getEvent().stream()
	            .filter(event -> event.getId().equals(ticketDto.getEventId())).findFirst().orElse(null);

	    if (selectedEvent == null) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Selected event is not available");
	    }

	    // Fetch the user entity based on email ID
	    User user = userService.findByEmailId(emailId);  // Assuming userService fetches the User entity by emailId

	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
	    }

	    // Create and save the ticket
	    Ticket ticket = new Ticket();
	    ticket.setUser(user);  // Set the User object
	    ticket.setEvent(selectedEvent);  // Set the Event object
	    ticket.setEventName(selectedEvent.getName());  // Set event name from the selected event
	    ticket.setStatus("Booked");  // Set the ticket status
	    ticket.setEventDate(selectedEvent.getStartDate());  // Set event date from the selected event

	    Ticket savedTicket = tservice.saveTicket(ticket);  // Save the ticket

	    return ResponseEntity.ok("Ticket booked successfully with ID: " + savedTicket.getId());
	}


	@GetMapping("/user/{userId}")
	public List<Ticket> getTicketsByUser(@PathVariable Long userId) {
		return tservice.getTicketsByUser(userId);
	}

	@GetMapping("/{id}")
	public Ticket getTicketById(@PathVariable Long id) {
		return tservice.getTicketById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
	}

	@PutMapping("/cancel/{id}")
	public void cancelTicket(@PathVariable Long id) {
		tservice.cancelTicket(id);
	}

	@GetMapping("/tickets/booked/{eventDate}")
    public List<Ticket> getUsersBookedOnDate(@PathVariable String eventDate) {
		 LocalDate date = LocalDate.parse(eventDate); // Parse the date string into a LocalDate
	        return tservice.getTicketsByEventDate(date); // Return the list of tickets
    }
}
