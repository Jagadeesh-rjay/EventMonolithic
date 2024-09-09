package com.rjay.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjay.user.entity.UserCalendar;

@Repository
public interface CalendarRepository extends JpaRepository<UserCalendar, Integer> {
    List<UserCalendar> findByUserIdAndEventId(Integer userId, Integer eventId);
}