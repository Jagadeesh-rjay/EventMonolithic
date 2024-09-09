package com.rjay.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class EventLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Street cannot be null")
    @Size(min = 1, max = 255, message = "Street must be between 1 and 255 characters long")
    private String street;

    @NotNull(message = "City cannot be null")
    @Size(min = 1, max = 100, message = "City must be between 1 and 100 characters long")
    private String city;

    @NotNull(message = "State cannot be null")
    @Size(min = 1, max = 100, message = "State must be between 1 and 100 characters long")
    private String state;

    @NotNull(message = "Pincode cannot be null")
    @Min(value = 100000, message = "Pincode must be 6 digits")
    @Max(value = 999999, message = "Pincode must be 6 digits")
    private int pincode;

    @DecimalMin(value = "-90.0", inclusive = true, message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", inclusive = true, message = "Latitude must be between -90 and 90")
    private Double latitude;

    @DecimalMin(value = "-180.0", inclusive = true, message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", inclusive = true, message = "Longitude must be between -180 and 180")
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false) // This will store event_id in the database
    private Event event;

    // Getters and Setters

}
