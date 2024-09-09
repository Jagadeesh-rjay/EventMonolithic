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

import com.rjay.user.entity.Image;
import com.rjay.user.service.ImgService;



@RestController
@RequestMapping("/image")
public class ImgController {
	
	@Autowired
	private ImgService service;
	
	
	 @PostMapping("/save")
    public ResponseEntity<String> uploadImage(
            @RequestParam("eventId") Long eventId,
           
            @RequestParam("data") MultipartFile[] files) {
       
        if (files.length != 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Please upload only one image file.");
        }

        MultipartFile file = files[0];

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The uploaded file is empty.");
        }

        try {
            Image image = service.upload(eventId, file);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Image uploaded successfully: " + image.getImgId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Image upload failed");
        }
    }
	 @GetMapping("event/{eventId}/{imageIndex}")
	 public ResponseEntity<byte[]> getImageByEvent(@PathVariable Long eventId, @PathVariable int imageIndex) {
	     List<Image> images = service.getImageByEvent(eventId);
	     
	     if (imageIndex >= images.size()) {
	         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
	     }

	     Image image = images.get(imageIndex);

	     return ResponseEntity.ok()
	             .contentType(MediaType.IMAGE_JPEG)
	             .body(image.getData());
	 }
	        	
	        @GetMapping("/{id}")
	        public ResponseEntity<byte[]> getMediaById(@PathVariable Long id) {
	            Optional<Image> mediaOptional = service.getImageById(id);

	            if (mediaOptional.isPresent()) {
	                Image media = mediaOptional.get();
	                HttpHeaders headers = new HttpHeaders();

	                String contentType = media.getFileType();
	                headers.setContentType(MediaType.parseMediaType(contentType));
	                headers.setContentLength(media.getData().length);

	                return new ResponseEntity<>(media.getData(), headers, HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            }
	}
	
	        @DeleteMapping("{id}")
	        public void DeleteImg(@PathVariable Long id) {
	    		Image image=service.Delete(id);
	    	
	    	}
	        
	        @PutMapping("/update/{imgId}") 
	        public ResponseEntity<String> updateImg(
	                @PathVariable Long imgId,
	                
	                @RequestParam("file") MultipartFile[] files) throws IOException {

	           
	            if (files.length != 1) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                        .body("Please upload exactly one image file.");
	            }

	            MultipartFile file = files[0];

	            if (file.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                        .body("The uploaded file is empty.");
	            }

	            try {
	                Image updatedImage = service.update(imgId, file);
	                return ResponseEntity.status(HttpStatus.OK)
	                        .body("Image updated successfully: " + updatedImage.getImgId());
	            } catch (IOException e) {
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                        .body("Image update failed");
	            } catch (IllegalArgumentException e) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                        .body(e.getMessage());
	            }
	        }

}
