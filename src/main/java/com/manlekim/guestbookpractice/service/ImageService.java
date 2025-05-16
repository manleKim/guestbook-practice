package com.manlekim.guestbookpractice.service;

import java.io.IOException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class ImageService {

    private static final Logger log = LoggerFactory.getLogger(ImageService.class);

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.base-url}")
    private String baseUrl;

    public ImageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Transactional
    public String createImage(MultipartFile imageFile) {
        try {
            String imageName = imageFile.getOriginalFilename();
            String contentType = imageFile.getContentType();
            long size = imageFile.getSize();

            String s3Key = UUID.randomUUID().toString() + "-" + imageName;

            // S3에 파일 업로드
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .contentType(contentType)
                .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(imageFile.getInputStream(), size));
            String s3Url = baseUrl + "/" + s3Key;
            log.info("Image uploaded successfully: {}", s3Key);

            return s3Url;
        } catch (IOException e) {
            log.error("Failed to upload image", e);
            throw new RuntimeException("Failed to upload image", e);
        }
    }

}
