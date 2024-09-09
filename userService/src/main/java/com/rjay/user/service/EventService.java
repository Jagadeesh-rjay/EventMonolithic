package com.rjay.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rjay.user.entity.Category;
import com.rjay.user.entity.Event;
import com.rjay.user.entity.EventManager;
import com.rjay.user.repository.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	public Event addEvent(Event event) {
		return eventRepository.save(event);
	}

	public Event getEventById(long eventId) {

		Optional<Event> optionalEvent = eventRepository.findById(eventId);

		if (optionalEvent.isPresent()) {
			return optionalEvent.get();
		} else {
			return null;
		}
	}

	public Event updateEvent(Event event) {
		return eventRepository.save(event); // Save the updated event
	}

	public List<Event> getAllEventByStatusIn(List<String> status) {
		return this.eventRepository.findByStatusIn(status);
	}

	public List<Event> searchEventNameAndStatusIn(String eventName, List<String> status) {
		return this.eventRepository.findByNameContainingIgnoreCaseAndStatusIn(eventName, status);
	}

	public List<Event> getAllEventByCategoryAndStatusIn(Category category, List<String> status) {
		return this.eventRepository.findByCategoryAndStatusIn(category, status);
	}

	public List<Event> getAllEventByManagerAndStatusIn(EventManager eventManager, List<String> statusList) {
		return eventRepository.findAllByEventManagerAndStatusIn(eventManager, statusList);
	}

	public boolean deleteEventById(long eventId) {
		Optional<Event> event = eventRepository.findById(eventId);
		if (event.isPresent()) {
			eventRepository.deleteById(eventId);
			return true;
		}
		return false;
	}

	public Optional<Event> getEventById(Long id) {
		return eventRepository.findById(id);
	}

	public Optional<Event> getEventByUserId(Long userId) {
		return eventRepository.findById(userId);
	}

	public int getTotalEvents() {

		return eventRepository.countAll();

	}

}
