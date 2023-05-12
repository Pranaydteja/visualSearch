package com.pr32.visualSearch.service;

import com.pr32.visualSearch.model.Image;
import com.pr32.visualSearch.repository.ImageRepository;
import com.pr32.visualSearch.util.GCPUploadUtil;
import com.pr32.visualSearch.util.GCPVisionUtil;
import com.pr32.visualSearch.util.LabelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ImageService {

    @Autowired
    GCPUploadUtil gcpUploadUtil;

    @Autowired
    private GCPVisionUtil visionUtil;

    @Autowired
    private LabelUtil labelUtil;

    @Autowired
    private ImageRepository imageRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    public Image uploadImage(MultipartFile file) {
        Image uploadedImage;
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) return null;
        Path path = new File(originalFileName).toPath();
        try {
            String contentType = Files.probeContentType(path);
            uploadedImage = gcpUploadUtil.uploadFile(file, originalFileName, contentType);
            if (uploadedImage != null) {
                LOGGER.info("Image uploaded successfully, file name: {} and url: {}", uploadedImage.getName(), uploadedImage.getUrl());
                Map<String, Float> extractedLabels = visionUtil.extractLabels(uploadedImage.getUrl());
                uploadedImage.setLabels(labelUtil.getLabels(extractedLabels));
                imageRepository.save(uploadedImage);
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while uploading. Error: ", e);
            return null;
        }
        return uploadedImage;
    }

    public List<String> getImageURLFromTag(String tag) {
        List<String> imageURLs = new ArrayList<>();
        List<Image> images = imageRepository.findByLabelsContainingIgnoreCase(tag);
        for(Image image:images){
            imageURLs.add(image.getUrl());
        }
        return imageURLs;
    }
}
