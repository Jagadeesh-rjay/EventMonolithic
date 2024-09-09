package com.rjay.user.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rjay.user.entity.Ticket;
import com.rjay.user.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	public Ticket bookTicket(Ticket ticket) {

		Ticket ticketSaved = ticketRepository.save(ticket);

		return ticketRepository.save(ticketSaved);
	}
	
    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

	public List<Ticket> getTicketsByUser(Long userId) {
		return ticketRepository.findByUserId(userId);
	}

	public Optional<Ticket> getTicketById(Long id) {
		return ticketRepository.findById(id);
	}

	public void cancelTicket(Long id) {
		Optional<Ticket> ticket = ticketRepository.findById(id);
		ticket.ifPresent(t -> {
			t.setStatus("CANCELED");
			ticketRepository.save(t);
		});
	}
	
	 public List<Ticket> getTicketsByEventDate(LocalDate date) {
	        LocalDateTime startOfDay = date.atStartOfDay();
	        LocalDateTime endOfDay = date.atTime(LocalTime.MAX); // End of the day
	        
	        return ticketRepository.findByEventDateBetween(startOfDay, endOfDay);
	    }
}