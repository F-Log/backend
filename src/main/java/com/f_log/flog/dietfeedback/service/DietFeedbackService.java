package com.f_log.flog.dietfeedback.service;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.diet.repository.DietRepository;
import com.f_log.flog.dietfeedback.domain.DietFeedback;
import com.f_log.flog.dietfeedback.dto.DietFeedbackDto;
import com.f_log.flog.dietfeedback.dto.DietFeedbackMapper;
import com.f_log.flog.dietfeedback.dto.DietFeedbackRequest;
import com.f_log.flog.dietfeedback.repository.DietFeedbackRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public DietFeedbackDto createDietFeedback(DietFeedbackRequest dietFeedbackRequest) {
        DietFeedback dietFeedback = new DietFeedback(dietFeedbackRequest.getDietFeedback());
        dietFeedbackRepository.save(dietFeedback);
        return dietFeedbackMapper.toDto(dietFeedback);
    }

    @Transactional
    public boolean updateDietFeedback(UUID dietfeedbackUuid, DietFeedbackRequest dietFeedbackRequest) {
        DietFeedback dietFeedback = dietFeedbackRepository.findByDietfeedbackUuidAndIsDeletedFalse(dietfeedbackUuid).orElse(null);
        if (dietFeedback != null) {
            dietFeedback.setDietFeedback(dietFeedbackRequest.getDietFeedback());
            return true;
        } else {
            return false;
        }
    }

    public DietFeedbackDto getDietFeedback(UUID dietfeedbackUuid) {
        DietFeedback dietFeedback = dietFeedbackRepository.findByDietfeedbackUuidAndIsDeletedFalse(dietfeedbackUuid).orElse(null);
        return dietFeedbackMapper.toDto(dietFeedback);
    }

    @Transactional
    public boolean deleteDietFeedback(UUID dietfeedbackUuid) {
        DietFeedback dietFeedback = dietFeedbackRepository.findByDietfeedbackUuidAndIsDeletedFalse(dietfeedbackUuid).orElse(null);
        if (dietFeedback != null) {
            dietFeedback.setDeleted();
            dietFeedbackRepository.save(dietFeedback);
            return true;
        } else {
            return false;
        }
    }
}
