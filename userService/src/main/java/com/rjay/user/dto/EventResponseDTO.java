package com.rjay.user.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjay.user.entity.Event;

public class EventResponseDTO extends CommonResponseAPI{
	
	private List<Event> event = new ArrayList<>();

	public List<Event> getEvent() {
		return event;
	}

	public void setEvent(List<Event> event) {
		this.event = event;
	}
	
	

}