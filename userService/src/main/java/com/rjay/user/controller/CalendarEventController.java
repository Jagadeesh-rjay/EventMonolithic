package com.rjay.user.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rjay.user.dto.EventResponseDTO;
import com.rjay.user.entity.Event;
import com.rjay.user.entity.Ticket;
import com.rjay.user.resource.EventResource;
import com.rjay.user.service.EventService;
import com.rjay.user.service.GoogleCalendarService;
import com.rjay.user.service.TicketService;



@RestController
@RequestMapping("/calendar")
public class CalendarEventController {

	@Autowired
	private EventResource eventResource;
	
	@Autowired
	private EventService eventService;

	/*
	 * @Autowired private EventServiceClient eventServiceClient;
	 * 
	 * @Autowired private TicketClient ticketClient; // Inject Feign client
	 */
	@Autowired
	private TicketService ticketService;

	@Autowired
	private GoogleCalendarService googleCalendarService;

	@Autowired
	public CalendarEventController(EventResource eventResource, TicketService ticketService) {
		this.eventResource = eventResource;
		this.ticketService = ticketService;
	}

	// Date not included in Event table and ticket table

	@GetMapping("/test")
	public String test() {
		return "your in Calendar Module";
	}

	@GetMapping("/booked/{eventDate}")
	public List<Ticket> getUsersBookedOnDate(@PathVariable String eventDate) {
		LocalDate date = LocalDate.parse(eventDate); // Parse the date string into a LocalDate
		return ticketService.getTicketsByEventDate(date); // Return the list of tickets
	}

	@GetMapping("/{eventId}")
	public ResponseEntity<EventResponseDTO> getEventById(@PathVariable int eventId) {
		// Call the Feign client to fetch the event details
		ResponseEntity<EventResponseDTO> eventResponse = eventResource.fetchEventById(eventId);

		// Return the response from Feign client
		return eventResponse;
	}

	@GetMapping("/event/{userId}")
	public ResponseEntity<Event> getEventByUserId(@PathVariable Long userId) {
		// Fetch tickets by userId using the Feign client
		List<Ticket> tickets = ticketService.getTicketsByUser(userId);

		if (tickets.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		// Logic to handle tickets or use tickets to retrieve the event
		// Assuming we fetch event based on userId
		Optional<Event> event = eventService.getEventByUserId(userId);

		return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	/*
	 * @GetMapping("/date/{date}") public List<Event> getEventsByDate(@PathVariable
	 * String date) { LocalDate eventDate = LocalDate.parse(date); return
	 * eventService.getEventsByDate(eventDate); }
	 * 
	 * @GetMapping("/all") public List<Event> getAllEventsOrderByDate() { return
	 * eventService.getAllEventsOrderByDate(); }
	 */

	/*
	 * @PostMapping("/sync") public String
	 * syncEventToGoogleCalendar(@RequestParam("userId") Integer userId,
	 * 
	 * @RequestParam("eventId") Integer eventId,
	 * 
	 * @RequestParam("title") String title,
	 * 
	 * @RequestParam("description") String description,
	 * 
	 * @RequestParam("startDateTime") String startDateTime,
	 * 
	 * @RequestParam("endDateTime") String endDateTime) { try {
	 * 
	 * startDateTime = cleanDateTimeString(startDateTime); endDateTime =
	 * cleanDateTimeString(endDateTime);
	 * 
	 * OffsetDateTime start = OffsetDateTime.parse(startDateTime); OffsetDateTime
	 * end = OffsetDateTime.parse(endDateTime);
	 * 
	 * googleCalendarService.syncEventToGoogleCalendar(userId, eventId, title,
	 * description, start, end); return "Event synced successfully!"; } catch
	 * (Exception e) { e.printStackTrace(); return "Error syncing event: " +
	 * e.getMessage(); } }
	 * 
	 * private String cleanDateTimeString(String dateTime) { // Remove any trailing
	 * dots or spaces return dateTime.replaceAll("\\.+$", ""); }
	 * 
	 */}
