package com.rjay.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reviews")
@Data
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reviewId;

	@Column(nullable = false)
	private Integer userId;

	@Column(nullable = false)
	private Integer eventId;

	@Column(nullable = false)
    @Min(1)
    @Max(5)
	private Integer rating;

	@Column(length = 1000)
	private String reviewText;

	@Column(nullable = false)
	private LocalDateTime reviewDate = LocalDateTime.now();

}
