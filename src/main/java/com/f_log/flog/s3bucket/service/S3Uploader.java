package com.f_log.flog.s3bucket.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.diet.repository.DietRepository;
import com.f_log.flog.s3bucket.dto.FileUploadResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    private final DietRepository dietRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public FileUploadResponse uploadFiles(UUID dietUuid, MultipartFile multipartFile, String dirName)
            throws IOException {
        Diet diet = dietRepository.findByDietUuidAndIsDeletedFalse(dietUuid)
                .orElseThrow(() -> new IllegalArgumentException("Diet not found with UUID: " + dietUuid));

        if (diet.getS3Url() != null) {
            throw new IllegalStateException("Diet already has an image URL. Use update method to change the image.");
        }

        String originalName = multipartFile.getOriginalFilename();
        String fileName = dirName + "/" + dietUuid.toString() + "-" + originalName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());

        amazonS3Client.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
        String fileUrl = amazonS3Client.getUrl(bucket, fileName).toString();

        diet.setS3Url(fileUrl);

        return new FileUploadResponse(originalName, fileUrl);
    }

    @Transactional
    public FileUploadResponse updateDietImageUrl(UUID dietUuid, MultipartFile newImageFile, String dirName) throws IOException {
        Diet diet = dietRepository.findByDietUuidAndIsDeletedFalse(dietUuid)
                .orElseThrow(() -> new IllegalArgumentException("Diet not found with UUID: " + dietUuid));

        if (diet.getS3Url() == null) {
            throw new IllegalStateException("Diet does not have an existing image URL. Please upload an image first.");
        }

        String originalName = newImageFile.getOriginalFilename();
        String fileName = dirName + "/" + dietUuid.toString() + "-" + originalName;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(newImageFile.getContentType());
        metadata.setContentLength(newImageFile.getSize());

        amazonS3Client.putObject(bucket, fileName, newImageFile.getInputStream(), metadata);
        String newImageUrl = amazonS3Client.getUrl(bucket, fileName).toString();

        diet.setS3Url(newImageUrl);

        return new FileUploadResponse(originalName, newImageUrl);
    }
}