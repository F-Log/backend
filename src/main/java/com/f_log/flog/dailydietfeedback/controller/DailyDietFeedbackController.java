package com.f_log.flog.dailydietfeedback.controller;

import com.f_log.flog.dailydietfeedback.dto.DailyDietFeedbackDto;
import com.f_log.flog.dailydietfeedback.dto.DailyDietFeedbackRequest;
import com.f_log.flog.dailydietfeedback.service.DailyDietFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/daily-diet-feedback")
@RequiredArgsConstructor
public class DailyDietFeedbackController {

    private final DailyDietFeedbackService dailyDietFeedbackService;

    @PostMapping("/new")
    public ResponseEntity<DailyDietFeedbackDto> createDietFeedback(@RequestBody DailyDietFeedbackRequest request) {
        DailyDietFeedbackDto created = dailyDietFeedbackService.createDailyDietFeedback(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{dietfeedbackUuid}")
    public ResponseEntity<DailyDietFeedbackDto> getDietFeedback(@PathVariable UUID dietfeedbackUuid) {
        DailyDietFeedbackDto found = dailyDietFeedbackService.getDailyDietFeedback(dietfeedbackUuid);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PutMapping("/{dietfeedbackUuid}")
    public ResponseEntity<DailyDietFeedbackDto> updateDietFeedback(@PathVariable UUID dietfeedbackUuid, @RequestBody DailyDietFeedbackRequest request) {
        boolean updated = dailyDietFeedbackService.updateDailyDietFeedback(dietfeedbackUuid, request);
        if (updated) {
            DailyDietFeedbackDto updatedFeedback = dailyDietFeedbackService.getDailyDietFeedback(dietfeedbackUuid);
            return ResponseEntity.ok(updatedFeedback);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{dietfeedbackUuid}")
    public ResponseEntity<Void> deleteDietFeedback(@PathVariable UUID dietfeedbackUuid) {
        boolean deleted = dailyDietFeedbackService.deleteDailyDietFeedback(dietfeedbackUuid);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
