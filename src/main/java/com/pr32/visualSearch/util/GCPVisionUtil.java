package com.pr32.visualSearch.util;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GCPVisionUtil {

    @Autowired
    CloudVisionTemplate cloudVisionTemplate;

    @Autowired
    private ResourceLoader resourceLoader;


    public Map<String, Float> extractLabels(String imageUrl) {

        AnnotateImageResponse response =
                this.cloudVisionTemplate.analyzeImage(
                        this.resourceLoader.getResource(imageUrl),
                        Feature.Type.LABEL_DETECTION,
                        Feature.Type.FACE_DETECTION,
                        Feature.Type.LANDMARK_DETECTION,
                        Feature.Type.LOGO_DETECTION,
                        Feature.Type.PRODUCT_SEARCH
                        );

        return response.getLabelAnnotationsList().stream()
                .collect(
                        Collectors.toMap(
                                EntityAnnotation::getDescription,
                                EntityAnnotation::getScore,
                                (u, v) -> {
                                    throw new IllegalStateException(String.format("Duplicate key %s", u));
                                },
                                LinkedHashMap::new));
    }
}
