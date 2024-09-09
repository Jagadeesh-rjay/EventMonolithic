package com.rjay.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjay.user.entity.Video;


@Repository
public interface VidRepo extends JpaRepository<Video,Long> {

	List<Video> findByEventId(Long eventId);

}
