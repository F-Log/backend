package com.f_log.flog.s3bucket.controller;

import com.f_log.flog.s3bucket.dto.ImageSaveDto;
import com.f_log.flog.s3bucket.service.ImageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/s3-upload")
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    public List<String> saveImage(@ModelAttribute ImageSaveDto imageSaveDto) {
        return imageService.saveImages(imageSaveDto);
    }
}
