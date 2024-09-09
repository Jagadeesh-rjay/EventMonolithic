package com.rjay.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rjay.user.dto.UserProfileDTO;
import com.rjay.user.entity.Review;
import com.rjay.user.resource.UserResource;
import com.rjay.user.service.ReviewService;
import com.rjay.user.service.UserService;



@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private UserResource userResource;

		
	 @GetMapping("/test")
		public String test() {
			return "your in reviews Module";
		}

	@GetMapping("/getAll")
	public List<Review> getAllReviews() {
		return reviewService.getAllReviews();
	}

	@GetMapping("/getBy/{reviewId}")
	public ResponseEntity<Review> getReviewById(@PathVariable Integer reviewId) {
		return reviewService.getReviewById(reviewId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/event/{eventId}")
	public List<Review> getReviewsByEventId(@PathVariable Integer eventId) {
		return reviewService.getReviewsByEventId(eventId);
	}

	/*
	 * @PostMapping public Review createReview(@RequestBody Review review) { return
	 * reviewService.createReview(review); }
	 */
	@PutMapping("/updateRating/{reviewId}")
	public Review updateRating(@PathVariable Integer reviewId, @RequestParam Integer newRating) {
		return reviewService.updateRating(reviewId, newRating);
	}

	@DeleteMapping("/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable Integer reviewId) {
	    reviewService.deleteReview(reviewId);
	    return ResponseEntity.ok("Review deleted successfully");
	}


	@PostMapping("/user/{emailId}/createReview")
	public ResponseEntity<?> createReview(@PathVariable String emailId, @RequestBody Review review) {
	    try {
	        // Fetch user profile using emailId
	        ResponseEntity<UserProfileDTO> response = userResource.getUserProfile(emailId);

	        if (response.getStatusCode().is2xxSuccessful()) {
	            UserProfileDTO userProfile = response.getBody();
	            if (userProfile != null && userProfile.isSuccess()) {
	                // Set the userId in the Review entity before saving
	                review.setUserId(userProfile.getId().intValue());
	                Review createdReview = reviewService.createReview(review);
	                return ResponseEntity.ok(createdReview);
	            } else {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body("User profile not found for email: " + emailId);
	            }
	        } else {
	            return ResponseEntity.status(response.getStatusCode()).build();
	        }
	    } catch (Exception e) {
	        // Handle other exceptions
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while creating the review: " + e.getMessage());
	    }
	}


	@GetMapping("/getProfile/{emailId}")
	public ResponseEntity<?> getUserProfileByEmail(@PathVariable String emailId) {
	    ResponseEntity<UserProfileDTO> response = userResource.getUserProfile(emailId);

	    if (response.getStatusCode().is2xxSuccessful()) {
	        UserProfileDTO userProfile = response.getBody();
	        if (userProfile != null) {
	            // Return the entire UserProfileDTO object
	            return ResponseEntity.ok(userProfile);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User profile not found for email: " + emailId);
	        }
	    } else {
	        return ResponseEntity.status(response.getStatusCode()).build();
	    }
	}


}
