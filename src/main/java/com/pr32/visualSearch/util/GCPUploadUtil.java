package com.pr32.visualSearch.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.pr32.visualSearch.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class GCPUploadUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(GCPUploadUtil.class);

    @Value("${gcp.config.file}")
    private String gcpConfigFile;
    @Value("${gcp.project.id}")
    private String gcpProjectId;
    @Value("${gcp.bucket.id}")
    private String gcpBucketId;
    @Value("${gcp.dir.name}")
    private String gcpDirectoryName;

    public Image uploadFile(MultipartFile multipartFile, String fileName, String contentType) {
        Image image = null;
        try {
            byte[] fileData = multipartFile.getBytes();
            InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();

            StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                    .setCredentials(GoogleCredentials.fromStream(inputStream)).build();

            Storage storage = options.getService();
            Bucket bucket = storage.get(gcpBucketId, Storage.BucketGetOption.fields());

            String id = UUID.randomUUID().toString();

            Blob blob = bucket.create(gcpDirectoryName + "/" + fileName + "-" + id + checkFileExtension(fileName), fileData, contentType);

            if (blob != null) {
                LOGGER.debug("File successfully uploaded to GCS");
                image = new Image(blob.getName(), blob.getMediaLink());
                image.setId(id);
            }

        } catch (IOException e) {
            LOGGER.debug("Error Uploading Image to GCP Storage!");
        }
        return image;
    }

    private String checkFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            String[] extensionList = {".png", ".jpeg", ".jpg"};
            for (String extension : extensionList) {
                if (fileName.endsWith(extension)) {
                    LOGGER.debug("Accepted file type : {}", extension);
                    return extension;
                }
            }
        }
        LOGGER.error("Not a permitted file type");
        return null;
    }


}