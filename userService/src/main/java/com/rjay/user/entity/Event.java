package com.rjay.user.entity;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @Size(max = 500)
    private String description;

	/*
	 * @NotNull(message = "location cannot be null") private String location;
	 */
    
 // Adding EventLocation as a ManyToOne relationship
	/*
	 * @ManyToOne(fetch = FetchType.EAGER)
	 * 
	 * @JoinColumn(name = "location_id") private EventLocation location;
	 */

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "eventManager_id")
    private EventManager eventManager;

    private String status;

    @NotNull(message = "Start date cannot be null")
    private LocalDateTime startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDateTime endDate;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
    }
    
    @OneToMany(mappedBy = "event")
    private Set<Image> images;

    @OneToMany(mappedBy = "event")
    private Set<Video> videos;
    
    
}
