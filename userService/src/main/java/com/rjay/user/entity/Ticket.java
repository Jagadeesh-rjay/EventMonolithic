package com.rjay.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;  // Linking the user entity
	
	@ManyToOne
	@JoinColumn(name = "event_id", referencedColumnName = "id")
	private Event event;  // Linking the event entity
	
	@NotBlank(message = "Event name cannot be blank")
	private String eventName;

	private String status;
	
	private LocalDateTime eventDate;
}
