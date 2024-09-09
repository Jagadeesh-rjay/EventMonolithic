package com.rjay.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjay.user.entity.Image;


@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {

	Image save(Image image);

	List<Image> findByEventId(Long eventId);
}
