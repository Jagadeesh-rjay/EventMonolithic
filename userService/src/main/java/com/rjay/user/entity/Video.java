package com.rjay.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
		private Long vidId;
	 @ManyToOne
	    @JoinColumn(name = "event_id", nullable = false)
	    @NotNull(message = "Event cannot be null")
	    private Event event;
	    
		 @Pattern(regexp = "video/(mp4|mpeg|quicktime)", 
	             message = "Invalid file type. Accepted types are video/mp4, video/mpeg, video/quicktime.")
		private String fileType;
		
		@Lob
		@Column(columnDefinition = "LONGBLOB")
		private byte[] data;

}
