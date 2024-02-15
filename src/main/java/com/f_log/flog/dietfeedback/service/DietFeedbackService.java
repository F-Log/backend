package com.f_log.flog.dietfeedback.service;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.diet.repository.DietRepository;
import com.f_log.flog.dietfeedback.domain.DietFeedback;
import com.f_log.flog.dietfeedback.dto.DietFeedbackDto;
import com.f_log.flog.dietfeedback.dto.DietFeedbackMapper;
import com.f_log.flog.dietfeedback.dto.DietFeedbackRequest;
import com.f_log.flog.dietfeedback.repository.DietFeedbackRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DietFeedbackService {
    private final DietRepository dietRepository;
    private final DietFeedbackRepository dietFeedbackRepository;
    private final DietFeedbackMapper dietFeedbackMapper;

    @Transactional
    public DietFeedbackDto createDietFeedback(UUID dietUuid, DietFeedbackRequest dietFeedbackRequest) {
        Diet diet = dietRepository.findDietByUuid(dietUuid);

        if (diet != null) {
            boolean feedbackExists = dietFeedbackRepository.existsByDietAndIsDeletedFalse(diet);

            if (!feedbackExists) {
                DietFeedback dietFeedback = new DietFeedback(dietFeedbackRequest.getDietFeedback(), diet);
                dietFeedbackRepository.save(dietFeedback);
                return dietFeedbackMapper.toDto(dietFeedback);
            } else {
                throw new RuntimeException("DietFeedback for Inbody with UUID " + dietUuid + " already exists.");
            }
        } else {
            throw new RuntimeException("Diet with UUID " + dietUuid + " not found or has been deleted.");
        }
    }

    @Transactional
    public boolean updateDietFeedback(Long feedbackId, DietFeedbackRequest dietFeedbackRequest) {
        DietFeedback dietFeedback = dietFeedbackRepository.findByIdAndIsDeletedFalse(feedbackId).orElse(null);
        if (dietFeedback != null) {
            dietFeedback.setDietFeedback(dietFeedbackRequest.getDietFeedback());
            return true;
        } else {
            return false;
        }
    }

    public DietFeedbackDto getDietFeedback(Long feedbackid) {
        DietFeedback dietFeedback = dietFeedbackRepository.findByIdAndIsDeletedFalse(feedbackid).orElse(null);
        return dietFeedbackMapper.toDto(dietFeedback);
    }

    @Transactional
    public boolean deleteDietFeedback(Long feedbackId) {
        DietFeedback dietFeedback = dietFeedbackRepository.findByIdAndIsDeletedFalse(feedbackId).orElse(null);
        if (dietFeedback != null) {
            dietFeedback.setDeleted();
            dietFeedbackRepository.save(dietFeedback);
            return true;
        } else {
            return false;
        }
    }
}
