package com.rjay.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rjay.user.entity.Category;
import com.rjay.user.entity.Event;
import com.rjay.user.entity.EventManager;




@Repository
public interface EventRepository extends JpaRepository<Event, Long>{

	
	List<Event> findByStatusIn(List<String> status);
	
	List<Event> findByNameContainingIgnoreCaseAndStatusIn(String eventName, List<String> status);
	
	List<Event> findByCategoryAndStatusIn(Category category, List<String> status);

	List<Event> findAllByEventManagerAndStatusIn(EventManager eventManager, List<String> status);
	
	@Query("SELECT COUNT(e) FROM Event e")
    int countAll();
}
