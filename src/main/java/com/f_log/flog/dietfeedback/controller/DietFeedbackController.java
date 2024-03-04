package com.f_log.flog.dietfeedback.controller;

import com.f_log.flog.dietfeedback.dto.DietFeedbackDto;
import com.f_log.flog.dietfeedback.dto.DietFeedbackRequest;
import com.f_log.flog.dietfeedback.service.DietFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/diet-feedback")
@RequiredArgsConstructor
public class DietFeedbackController {

    private final DietFeedbackService dietFeedbackService;

    @PostMapping("/new")
    public ResponseEntity<DietFeedbackDto> createDietFeedback(@RequestBody DietFeedbackRequest request) {
        DietFeedbackDto created = dietFeedbackService.createDietFeedback(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{feedbackId}")
    public ResponseEntity<DietFeedbackDto> getDietFeedback(@PathVariable Long feedbackId) {
        DietFeedbackDto found = dietFeedbackService.getDietFeedback(feedbackId);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PutMapping("/{feedbackId}")
    public ResponseEntity<DietFeedbackDto> updateDietFeedback(@PathVariable Long feedbackId, @RequestBody DietFeedbackRequest request) {
        boolean updated = dietFeedbackService.updateDietFeedback(feedbackId, request);
        if (updated) {
            DietFeedbackDto updatedFeedback = dietFeedbackService.getDietFeedback(feedbackId);
            return ResponseEntity.ok(updatedFeedback);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<Void> deleteDietFeedback(@PathVariable Long feedbackId) {
        boolean deleted = dietFeedbackService.deleteDietFeedback(feedbackId);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
