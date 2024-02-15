package com.f_log.flog.inbodyfeedback.controller;

import com.f_log.flog.inbodyfeedback.domain.InbodyFeedback;
import com.f_log.flog.inbodyfeedback.dto.InbodyFeedbackMapper;
import com.f_log.flog.inbodyfeedback.dto.InbodyFeedbackRequestDto;
import com.f_log.flog.inbodyfeedback.dto.InbodyFeedbackResponseDto;
import com.f_log.flog.inbodyfeedback.service.InbodyFeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inbody-feedback")
public class InbodyFeedbackController {

    private final InbodyFeedbackService inbodyFeedbackService;
    private final InbodyFeedbackMapper inbodyFeedbackMapper;

    public InbodyFeedbackController(InbodyFeedbackService inbodyFeedbackService, InbodyFeedbackMapper inbodyFeedbackMapper) {
        this.inbodyFeedbackService = inbodyFeedbackService;
        this.inbodyFeedbackMapper = inbodyFeedbackMapper;
    }

    @PostMapping("/{inbodyUuid}")
    public ResponseEntity<InbodyFeedbackResponseDto> createInbodyFeedback(@PathVariable UUID inbodyUuid, @RequestBody InbodyFeedbackRequestDto requestDto) {
        InbodyFeedback createdInbodyFeedback = inbodyFeedbackService.createInbodyFeedback(inbodyUuid, requestDto);
        InbodyFeedbackResponseDto responseDto = inbodyFeedbackMapper.toDto(createdInbodyFeedback);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{feedbackId}")
    public ResponseEntity<InbodyFeedbackResponseDto> updateInbodyFeedback(@PathVariable Long feedbackId, @RequestBody InbodyFeedbackRequestDto requestDto) {
        InbodyFeedback updatedInbodyFeedback = inbodyFeedbackService.updateInbodyFeedback(feedbackId, requestDto);
        InbodyFeedbackResponseDto responseDto = inbodyFeedbackMapper.toDto(updatedInbodyFeedback);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{feedbackId}")
    public ResponseEntity<InbodyFeedbackResponseDto> getInbodyFeedback(@PathVariable Long feedbackId) {
        InbodyFeedback inbodyFeedback = inbodyFeedbackService.getInbodyFeedback(feedbackId);
        InbodyFeedbackResponseDto responseDto = inbodyFeedbackMapper.toDto(inbodyFeedback);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<Void> deleteInbodyFeedback(@PathVariable Long feedbackId) {
        inbodyFeedbackService.softDeleteInbodyFeedback(feedbackId);
        return ResponseEntity.noContent().build();
    }
}
