package com.rjay.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rjay.user.entity.Review;
import com.rjay.user.exception.RatingValueException;
import com.rjay.user.exception.ResourceNotFoundException;
import com.rjay.user.exception.ReviewTextException;
import com.rjay.user.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	public List<Review> getAllReviews() {
		return reviewRepository.findAll();
	}

	public Optional<Review> getReviewById(Integer reviewId) {
		return reviewRepository.findById(reviewId);
	}

	public List<Review> getReviewsByEventId(Integer eventId) {
		return reviewRepository.findByEventId(eventId);
	}

	public List<Review> getReviewsByUserId(Integer userId) {
		return reviewRepository.findByUserId(userId);
	}

	public Review createReview(Review review) {
		// added for validation
		if (review.getRating() < 1 || review.getRating() > 5) {
			throw new RatingValueException("Rating must be between 1 and 5");
		}

		if (review.getReviewText().equals("") || review.getReviewText().equals(null) || review.getReviewText().isEmpty()
				|| review.getReviewText().isBlank()) {
			throw new ReviewTextException("Review Text Must not be null");
		}

		return reviewRepository.save(review);
	}

	public Review updateRating(Integer reviewId, Integer newRating) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));
		review.setRating(newRating);
		return reviewRepository.save(review);
	}

	public void deleteReview(Integer reviewId) {
	    if (!reviewRepository.existsById(reviewId)) {
	        throw new ResourceNotFoundException("Review with ID " + reviewId + " not found");
	    }
	    reviewRepository.deleteById(reviewId);
	}
}
