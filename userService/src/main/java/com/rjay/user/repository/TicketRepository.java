package com.rjay.user.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjay.user.entity.Ticket;



@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	List<Ticket> findByUserId(Long userId);
	
	 List<Ticket> findByEventDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

}
