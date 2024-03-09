package com.f_log.flog.dietfeedback.controller;

import com.f_log.flog.dietfeedback.dto.DietFeedbackDto;
import com.f_log.flog.dietfeedback.dto.DietFeedbackRequest;
import com.f_log.flog.dietfeedback.service.DietFeedbackService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{dietfeedbackUuid}")
    public ResponseEntity<DietFeedbackDto> getDietFeedback(@PathVariable UUID dietfeedbackUuid) {
        DietFeedbackDto found = dietFeedbackService.getDietFeedback(dietfeedbackUuid);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PutMapping("/{dietfeedbackUuid}")
    public ResponseEntity<DietFeedbackDto> updateDietFeedback(@PathVariable UUID dietfeedbackUuid, @RequestBody DietFeedbackRequest request) {
        boolean updated = dietFeedbackService.updateDietFeedback(dietfeedbackUuid, request);
        if (updated) {
            DietFeedbackDto updatedFeedback = dietFeedbackService.getDietFeedback(dietfeedbackUuid);
            return ResponseEntity.ok(updatedFeedback);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{dietfeedbackUuid}")
    public ResponseEntity<Void> deleteDietFeedback(@PathVariable UUID dietfeedbackUuid) {
        boolean deleted = dietFeedbackService.deleteDietFeedback(dietfeedbackUuid);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
