package com.pr32.visualSearch.controller;

import com.pr32.visualSearch.dto.Tag;
import com.pr32.visualSearch.dto.UploadImageResponse;
import com.pr32.visualSearch.model.Image;
import com.pr32.visualSearch.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@CrossOrigin
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadImageResponse> uploadImage(@RequestParam("image") MultipartFile file) {
        Image uploadImage = imageService.uploadImage(file);
        if (uploadImage == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        UploadImageResponse response = new UploadImageResponse();
        response.setLabels(uploadImage.getLabels());
        response.setUrl(uploadImage.getUrl());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/searchByTag")
    public ResponseEntity<List<String>> getImagesByTags(@RequestBody Tag tag) {
        List<String> URLs = imageService.getImageURLFromTag(tag.getValue());
        System.out.println(URLs);
        return ResponseEntity.status(HttpStatus.OK).body(URLs);
    }

}
