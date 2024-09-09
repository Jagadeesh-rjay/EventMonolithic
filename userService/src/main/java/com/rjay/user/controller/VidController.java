package com.rjay.user.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.rjay.user.entity.Video;
import com.rjay.user.service.VidService;


@RestController
@RequestMapping("/video")
public class VidController {
	@Autowired
	private VidService service;
	
	
	@PostMapping("/save")
	public ResponseEntity<String> uploadVideo(
            @RequestParam("eventId") Long eventId,
            @RequestParam("data") MultipartFile[] files) {
       
        if (files.length != 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Please upload only one video file.");
        }

        MultipartFile file = files[0];

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The uploaded file is empty.");
        }

        try {
            Video video = service.upload(eventId,file);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Video uploaded successfully: " + video.getVidId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Image upload failed");
        }
	}    
	      
	        	
	        @GetMapping("/{id}")
	        public ResponseEntity<byte[]> getMediaById(@PathVariable Long id) {
	            Optional<Video> mediaOptional = service.getVideoById(id);

	            if (mediaOptional.isPresent()) {
	                Video media = mediaOptional.get();
	                HttpHeaders headers = new HttpHeaders();

	                String contentType = media.getFileType();
	                headers.setContentType(MediaType.parseMediaType(contentType));
	                headers.setContentLength(media.getData().length);

	                return new ResponseEntity<>(media.getData(), headers, HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            }
	}
	        @GetMapping("event/{eventId}/{videoIndex}")
	   	 public ResponseEntity<byte[]> getVideoByEvent(@PathVariable Long eventId, @PathVariable int videoIndex) {
	   	     List<Video> video = service.getVideoByEvent(eventId);
	   	     
	   	     if (videoIndex >= video.size()) {
	   	         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "video not found");
	   	     }

	   	     Video video1 = video.get(videoIndex);

	   	     return ResponseEntity.ok()
	   	    		.contentType(MediaType.valueOf("video/mp4"))
	   	             .body(video1.getData());
	   	 }
	
	        @DeleteMapping("/{id}")
	        public void DeleteImg(@PathVariable Long id) {
	    		Video video=service.Delete(id);
	    	
	    	}
	        
	        @PutMapping("/update/{imgId}")
	        public ResponseEntity<String> updateImg(
	                @PathVariable Long imgId,
	              
	                @RequestParam("file") MultipartFile[] files) throws IOException {

	           
	            if (files.length != 1) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                        .body("Please upload exactly one video file.");
	            }

	            MultipartFile file = files[0];

	            if (file.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                        .body("The uploaded file is empty.");
	            }

	            try {
	                Video updatedVideo = service.update(imgId,file);
	                return ResponseEntity.status(HttpStatus.OK)
	                        .body("Video updated successfully: " + updatedVideo.getVidId());
	            } catch (IOException e) {
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                        .body("Video update failed");
	            } catch (IllegalArgumentException e) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                        .body(e.getMessage());
	            }
	        }
	

}
