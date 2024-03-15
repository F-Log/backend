package com.f_log.flog.dailydietfeedback.service;

import com.f_log.flog.dailydietfeedback.domain.DailyDietFeedback;
import com.f_log.flog.dailydietfeedback.dto.DailyDietFeedbackDto;
import com.f_log.flog.dailydietfeedback.dto.DailyDietFeedbackMapper;
import com.f_log.flog.dailydietfeedback.dto.DailyDietFeedbackRequest;
import com.f_log.flog.dailydietfeedback.repository.DailyDietFeedbackRepository;
import com.f_log.flog.diet.repository.DietRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DailyDietFeedbackService {
    private final DietRepository dietRepository;
    private final DailyDietFeedbackRepository dailyDietFeedbackRepository;
    private final DailyDietFeedbackMapper dailyDietFeedbackMapper;

    @Transactional
    public DailyDietFeedbackDto createDailyDietFeedback(DailyDietFeedbackRequest dailyDietFeedbackRequest) {
        DailyDietFeedback dailyDietFeedback = new DailyDietFeedback(dailyDietFeedbackRequest.getContent());
        dailyDietFeedbackRepository.save(dailyDietFeedback);
        return dailyDietFeedbackMapper.toDto(dailyDietFeedback);
    }

    @Transactional
    public boolean updateDailyDietFeedback(UUID dietfeedbackUuid, DailyDietFeedbackRequest dailyDietFeedbackRequest) {
        DailyDietFeedback dailyDietFeedback = dailyDietFeedbackRepository.findByDietfeedbackUuidAndIsDeletedFalse(dietfeedbackUuid).orElse(null);
        if (dailyDietFeedback != null) {
            dailyDietFeedback.setDietFeedback(dailyDietFeedbackRequest.getContent());
            return true;
        } else {
            return false;
        }
    }

    public DailyDietFeedbackDto getDailyDietFeedback(UUID dailyDietFeedbackUuid) {
        DailyDietFeedback dailyDietFeedback = dailyDietFeedbackRepository.findByDietfeedbackUuidAndIsDeletedFalse(dailyDietFeedbackUuid).orElse(null);
        return dailyDietFeedbackMapper.toDto(dailyDietFeedback);
    }

    @Transactional
    public boolean deleteDailyDietFeedback(UUID dietfeedbackUuid) {
        DailyDietFeedback dailyDietFeedback = dailyDietFeedbackRepository.findByDietfeedbackUuidAndIsDeletedFalse(dietfeedbackUuid).orElse(null);
        if (dailyDietFeedback != null) {
            dailyDietFeedback.setDeleted();
            dailyDietFeedbackRepository.save(dailyDietFeedback);
            return true;
        } else {
            return false;
        }
    }
}
