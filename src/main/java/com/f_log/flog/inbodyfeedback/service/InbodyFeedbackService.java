package com.f_log.flog.inbodyfeedback.service;

import com.f_log.flog.inbody.domain.Inbody;
import com.f_log.flog.inbody.repository.InbodyRepository;
import com.f_log.flog.inbodyfeedback.domain.InbodyFeedback;
import com.f_log.flog.inbodyfeedback.dto.InbodyFeedbackRequestDto;
import com.f_log.flog.inbodyfeedback.repository.InbodyFeedbackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class InbodyFeedbackService {

    private final InbodyFeedbackRepository inbodyFeedbackRepository;
    private final InbodyRepository inbodyRepository;

    public InbodyFeedbackService(InbodyFeedbackRepository inbodyFeedbackRepository, InbodyRepository inbodyRepository) {
        this.inbodyFeedbackRepository = inbodyFeedbackRepository;
        this.inbodyRepository = inbodyRepository;
    }

    @Transactional
    public InbodyFeedback createInbodyFeedback(InbodyFeedbackRequestDto requestDto) {
        // Inbody 객체 가져오기
        UUID inbodyUuid = requestDto.getInbodyUuid();
        Inbody inbody = inbodyRepository.findByInbodyUuidAndIsDeletedFalse(inbodyUuid);

        if (inbody != null) {
            // 중복 체크를 위해 해당 Inbody에 대한 InbodyFeedback이 이미 존재하는지 확인
            boolean feedbackExists = inbodyFeedbackRepository.existsByInbodyAndIsDeletedFalse(inbody);

            if (!feedbackExists) {
                // 새로운 InbodyFeedback 생성 및 저장
                InbodyFeedback inbodyFeedback = new InbodyFeedback(requestDto.getInbodyFeedback(), inbody);
                return inbodyFeedbackRepository.save(inbodyFeedback);
            } else {
                throw new RuntimeException("InbodyFeedback for Inbody with UUID " + inbodyUuid + " already exists.");
            }
        } else {
            throw new RuntimeException("Inbody with UUID " + inbodyUuid + " not found or has been deleted.");
        }
    }

    @Transactional
    public InbodyFeedback updateInbodyFeedback(Long feedbackId, InbodyFeedbackRequestDto requestDto) {
        InbodyFeedback inbodyFeedback = getInbodyFeedback(feedbackId);
        InbodyFeedback updatedInbodyFeedback = new InbodyFeedback(requestDto.getInbodyFeedback(), inbodyFeedback.getInbody());
        updatedInbodyFeedback.setId(inbodyFeedback.getId());
        return inbodyFeedbackRepository.save(updatedInbodyFeedback);
    }


    @Transactional(readOnly = true)
    public InbodyFeedback getInbodyFeedback(Long feedbackId) {
        return inbodyFeedbackRepository.findByIdAndIsDeletedFalse(feedbackId)
                .orElseThrow(() -> new RuntimeException("InbodyFeedback with ID " + feedbackId + " not found or has been deleted."));
    }

    @Transactional
    public void softDeleteInbodyFeedback(Long feedbackId) {
        InbodyFeedback inbodyFeedback = getInbodyFeedback(feedbackId);
        inbodyFeedback.setDeleted();
        inbodyFeedbackRepository.save(inbodyFeedback);
    }
}
