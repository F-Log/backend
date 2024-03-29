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

    @PostMapping("/new")
    public ResponseEntity<InbodyFeedbackResponseDto> createInbodyFeedback(@RequestBody InbodyFeedbackRequestDto requestDto) {
        InbodyFeedback createdInbodyFeedback = inbodyFeedbackService.createInbodyFeedback(requestDto);
        InbodyFeedbackResponseDto responseDto = inbodyFeedbackMapper.toDto(createdInbodyFeedback);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{feedbackUuid}")
    public ResponseEntity<InbodyFeedbackResponseDto> updateInbodyFeedback(@PathVariable UUID feedbackUuid, @RequestBody InbodyFeedbackRequestDto requestDto) {
        InbodyFeedback updatedInbodyFeedback = inbodyFeedbackService.updateInbodyFeedback(feedbackUuid, requestDto);
        InbodyFeedbackResponseDto responseDto = inbodyFeedbackMapper.toDto(updatedInbodyFeedback);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{feedbackUuid}")
    public ResponseEntity<InbodyFeedbackResponseDto> getInbodyFeedback(@PathVariable UUID feedbackUuid) {
        InbodyFeedback inbodyFeedback = inbodyFeedbackService.getInbodyFeedback(feedbackUuid);
        InbodyFeedbackResponseDto responseDto = inbodyFeedbackMapper.toDto(inbodyFeedback);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{feedbackUuid}")
    public ResponseEntity<Void> deleteInbodyFeedback(@PathVariable UUID feedbackUuid) {
        inbodyFeedbackService.softDeleteInbodyFeedback(feedbackUuid);
        return ResponseEntity.noContent().build();
    }
}
