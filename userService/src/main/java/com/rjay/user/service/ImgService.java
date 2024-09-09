package com.rjay.user.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rjay.user.entity.Event;
import com.rjay.user.entity.Image;
import com.rjay.user.repository.EventRepository;
import com.rjay.user.repository.ImageRepo;


@Service
public class ImgService {
	
	
	@Autowired
	private ImageRepo repo;

	@Autowired
	private EventRepository erepo;
	
	public Image upload(Long eventId,MultipartFile file) throws IOException {
		Event event = erepo.findById(eventId)
                .orElseThrow();
		  String fileType=file.getContentType();
		  Image image = new Image();
	        image.setEvent(event); 
	    image.setFileType(fileType);
	    image.setData(file.getBytes());

	    return repo.save(image);

	}
	

	
	public Optional<Image> getImageById(Long id){
		return repo.findById(id);
		
	}
	public Image Delete(Long id) {
		Image image=repo.findById(id).get();
		 repo.delete(image);
		return image;
		
	}
	
	 public List<Image> getImageByEvent(Long eventId) {
	        return repo.findByEventId(eventId);
	    }
	
	public Image update(Long imgId,MultipartFile file) throws IOException {
		Image img1=repo.findById(imgId).get();
		String fileType=file.getContentType();
		img1.setFileType(fileType);
		img1.setData(file.getBytes());
		return repo.save(img1);
		
	}




	
	}
