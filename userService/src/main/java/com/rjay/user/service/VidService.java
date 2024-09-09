package com.rjay.user.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.rjay.user.entity.Event;
import com.rjay.user.entity.Video;
import com.rjay.user.repository.EventRepository;
import com.rjay.user.repository.VidRepo;



@Service
public class VidService {
	@Autowired
	private VidRepo repo;

	@Autowired
	private EventRepository erepo;
	
	public Video upload(Long eventId, MultipartFile file) throws IOException {
		
		  Event event = erepo.findById(eventId)
                .orElseThrow();
		  String fileType=file.getContentType();
		  Video video = new Video();
	        video.setEvent(event);
	        video.setFileType(fileType);
	        video.setData(file.getBytes());

  return repo.save(video);

	}
	

	
	public Optional<Video> getVideoById(Long id){
		return repo.findById(id);
		
	}
	public Video Delete(Long id) {
		Video video=repo.findById(id).get();
		 repo.delete(video);
		return video;
	
	}
	
	    public List<Video> getVideoByEvent(@PathVariable Long eventId) {
	        return repo.findByEventId(eventId);
	    }
	
	public Video update(Long vidId,MultipartFile file) throws IOException {
		Video vid1=repo.findById(vidId).get();
		String fileType=file.getContentType();
		vid1.setFileType(fileType);
		vid1.setData(file.getBytes());
		return repo.save(vid1);
		
	}

}
