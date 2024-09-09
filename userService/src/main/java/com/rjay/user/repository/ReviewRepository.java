package com.rjay.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjay.user.entity.Review;



@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByEventId(Integer eventId);
    List<Review> findByUserId(Integer userId);
}
