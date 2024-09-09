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
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long imgId;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @NotNull(message = "Event cannot be null")
    private Event event;

	 @Pattern(regexp = "image/(jpeg|png|gif)", 
             message = "Invalid file type. Accepted types are image/jpeg, image/png, image/gif")
	private String fileType;
	
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] data;
	
	
}
