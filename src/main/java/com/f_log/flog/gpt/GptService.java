package com.f_log.flog.gpt;

import com.f_log.flog.diet.dto.DailyIntakeDto;
import com.f_log.flog.diet.dto.DietDto;
import com.f_log.flog.dietfeedback.dto.DietFeedbackDto;
import com.f_log.flog.exercise.dto.ExerciseResponseDto;
import com.f_log.flog.inbody.dto.InbodyResponseDto;
import com.f_log.flog.inbodyfeedback.dto.InbodyFeedbackResponseDto;
import com.f_log.flog.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GptService {

    private final RestTemplate restTemplate;

    public ResponseEntity<DietFeedbackDto> createDietFeedback(LocalDate date, MemberResponseDto memberResponseDto, DailyIntakeDto dailyIntakeDto, InbodyResponseDto inbodyResponseDto, ExerciseResponseDto exerciseResponseDto) {
        Map<String, Object> dataMap = createDietDataMap(date, memberResponseDto, dailyIntakeDto, inbodyResponseDto, exerciseResponseDto);
        ResponseEntity<DietFeedbackDto> responseEntity = exchangeDietData(dataMap);
        return responseEntity;
    }

    private Map<String, Object> createDietDataMap(LocalDate date, MemberResponseDto memberResponseDto, DailyIntakeDto dailyIntakeDto, InbodyResponseDto inbodyResponseDto, ExerciseResponseDto exerciseResponseDto) {
        Map<String, Object> dataMap = new HashMap<>();
        // diet info
        dataMap.put("date", date);
        dataMap.put("totalCarbohydrate", dailyIntakeDto.getTotalCarbohydrate());
        dataMap.put("totalProtein", dailyIntakeDto.getTotalProtein());
        dataMap.put("totalSodium", dailyIntakeDto.getTotalSodium());
        dataMap.put("totalFat", dailyIntakeDto.getTotalFat());
        dataMap.put("totalCholesterol", dailyIntakeDto.getTotalCholesterol());
        dataMap.put("totalSugars", dailyIntakeDto.getTotalSugars());
        dataMap.put("totalCalories", dailyIntakeDto.getTotalCalories());

        // member and inbody info
        dataMap.put("memberUuid", memberResponseDto.getUuid());
        dataMap.put("gender", memberResponseDto.getGender());
        dataMap.put("height", inbodyResponseDto.getHeight());
        dataMap.put("bodyWeight", inbodyResponseDto.getBodyWeight());
        dataMap.put("fatMass", inbodyResponseDto.getFatMass());
        dataMap.put("basalMetabolicRate", inbodyResponseDto.getBasalMetabolicRate());
        dataMap.put("bodyFatPercentage", inbodyResponseDto.getBodyFatPercentage());
        dataMap.put("muscleMass", inbodyResponseDto.getMuscleMass());
        dataMap.put("exercisePurpose", exerciseResponseDto.getExercisePurpose());
        return dataMap;
    }

    private ResponseEntity<DietFeedbackDto> exchangeDietData(Map<String, Object> dataMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(dataMap, headers);
        ResponseEntity<DietFeedbackDto> responseEntity = restTemplate.exchange("http://127.0.0.1:5000/receive-diet", HttpMethod.POST, requestEntity, DietFeedbackDto.class);
        DietFeedbackDto body = responseEntity.getBody();
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    public ResponseEntity<InbodyFeedbackResponseDto> createInbodyFeedback(UUID inbodyUuid, InbodyResponseDto inbodyResponseDto, MemberResponseDto memberResponseDto, ExerciseResponseDto exerciseResponseDto) {
        Map<String, Object> dataMap = createInbodyDataMap(inbodyUuid, inbodyResponseDto, memberResponseDto, exerciseResponseDto);
        ResponseEntity<InbodyFeedbackResponseDto> responseEntity = exchangeInbodyData(dataMap);
        return responseEntity;
    }

    private Map<String, Object> createInbodyDataMap(UUID inbodyUuid, InbodyResponseDto inbodyResponseDto, MemberResponseDto memberResponseDto, ExerciseResponseDto exerciseResponseDto) {
        Map<String, Object> dataMap = new HashMap<>();
        // member and inbody info
        dataMap.put("inbodyUuid", inbodyUuid);
        dataMap.put("height", inbodyResponseDto.getHeight());
        dataMap.put("bodyWeight", inbodyResponseDto.getBodyWeight());
        dataMap.put("fatMass", inbodyResponseDto.getFatMass());
        dataMap.put("basalMetabolicRate", inbodyResponseDto.getBasalMetabolicRate());
        dataMap.put("bodyFatPercentage", inbodyResponseDto.getBodyFatPercentage());
        dataMap.put("muscleMass", inbodyResponseDto.getMuscleMass());
        dataMap.put("gender", memberResponseDto.getGender());
        dataMap.put(("exercisePurpose"), exerciseResponseDto.getExercisePurpose());
        return dataMap;
    }

    private ResponseEntity<InbodyFeedbackResponseDto> exchangeInbodyData(Map<String, Object> dataMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(dataMap, headers);
        ResponseEntity<InbodyFeedbackResponseDto> responseEntity = restTemplate.exchange("http://127.0.0.1:5000/receive-inbody", HttpMethod.POST, requestEntity, InbodyFeedbackResponseDto.class);
        InbodyFeedbackResponseDto body = responseEntity.getBody();
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

}
