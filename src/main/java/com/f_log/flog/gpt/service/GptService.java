package com.f_log.flog.gpt.service;

import com.f_log.flog.allergy.dto.AllergyDto;
import com.f_log.flog.dailydietfeedback.dto.DailyDietFeedbackDto;
import com.f_log.flog.diet.dto.DailyIntakeDto;
import com.f_log.flog.diet.dto.DietDto;
import com.f_log.flog.dietfeedback.dto.DietFeedbackDto;
import com.f_log.flog.dietfood.dto.DietFoodResponseDto;
import com.f_log.flog.exercise.dto.ExerciseResponseDto;
import com.f_log.flog.healthinformation.domain.DailyActivity;
import com.f_log.flog.healthinformation.dto.HealthInformationResponseDto;
import com.f_log.flog.inbody.dto.InbodyResponseDto;
import com.f_log.flog.inbodyfeedback.dto.InbodyFeedbackResponseDto;
import com.f_log.flog.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GptService {

    private final RestTemplate restTemplate;

    // 특정 식단 피드백
    public ResponseEntity<DietFeedbackDto> createDietFeedback(DietDto dietDto, List<AllergyDto> allergyDtos) {
        Map<String, Object> dataMap = createDietMap(dietDto, allergyDtos);
        ResponseEntity<DietFeedbackDto> responseEntity = exchangeDietData(dataMap);
        return responseEntity;
    }

    private Map<String, Object> createDietMap(DietDto dietDto, List<AllergyDto> allergyDtos) {
        Map<String, Object> dataMap = new HashMap<>();

        // Put nutrition info of Diet entity
        dataMap.put("dietUuid", dietDto.getDietUuid().toString());
        dataMap.put("totalCarbohydrate", dietDto.getTotalCarbohydrate());
        dataMap.put("totalProtein", dietDto.getTotalProtein());
        dataMap.put("totalFat", dietDto.getTotalFat());
        dataMap.put("totalSodium", dietDto.getTotalSodium());
        dataMap.put("totalCholesterol", dietDto.getTotalCholesterol());
        dataMap.put("totalSugars", dietDto.getTotalSugars());
        dataMap.put("totalCalories", dietDto.getTotalCalories());
        dataMap.put("mealType", dietDto.getMealType().toString());
        dataMap.put("mealDate", dietDto.getMealDate().toString());

        // Put all foodNames of each DietFoods in List<DietFood> of Diet field
        List<String> foodNames = dietDto.getDietFoods().stream()
                .map(DietFoodResponseDto::getFoodName)
                .collect(Collectors.toList());
        dataMap.put("foodNames", foodNames);

        // Add allergy information
        List<String> allergies = allergyDtos.stream()
                .map(AllergyDto::getAllergy)
                .collect(Collectors.toList());
        dataMap.put("allergies", allergies);

        return dataMap;
    }

    private ResponseEntity<DietFeedbackDto> exchangeDietData(Map<String, Object> dataMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(dataMap, headers);
        ResponseEntity<DietFeedbackDto> responseEntity = restTemplate.exchange(
                "http://ai:5000/receive-diet", HttpMethod.POST, requestEntity, DietFeedbackDto.class);
        DietFeedbackDto body = responseEntity.getBody();
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    // 하루 총 영양섭취 피드백
    public ResponseEntity<DailyDietFeedbackDto> createDailyDietFeedback(LocalDate date, MemberResponseDto memberResponseDto, DailyIntakeDto dailyIntakeDto, InbodyResponseDto inbodyResponseDto, ExerciseResponseDto exerciseResponseDto) {
        Map<String, Object> dataMap = createDailyDietMap(date, memberResponseDto, dailyIntakeDto, inbodyResponseDto, exerciseResponseDto);
        ResponseEntity<DailyDietFeedbackDto> responseEntity = exchangeDailyDietData(dataMap);
        return responseEntity;
    }

    private Map<String, Object> createDailyDietMap(LocalDate date, MemberResponseDto memberResponseDto, DailyIntakeDto dailyIntakeDto, InbodyResponseDto inbodyResponseDto, ExerciseResponseDto exerciseResponseDto) {
        Map<String, Object> dataMap = new HashMap<>();

        // daily total nutrition info
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

    private ResponseEntity<DailyDietFeedbackDto> exchangeDailyDietData(Map<String, Object> dataMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(dataMap, headers);
        ResponseEntity<DailyDietFeedbackDto> responseEntity = restTemplate.exchange("http://ai:5000/receive-daily-diet", HttpMethod.POST, requestEntity, DailyDietFeedbackDto.class);
        DailyDietFeedbackDto body = responseEntity.getBody();
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    public ResponseEntity<InbodyFeedbackResponseDto> createInbodyFeedback(UUID inbodyUuid, InbodyResponseDto inbodyResponseDto, MemberResponseDto memberResponseDto, ExerciseResponseDto exerciseResponseDto, Optional<HealthInformationResponseDto> healthInformationResponseDto) {
        Map<String, Object> dataMap = createInbodyDataMap(inbodyUuid, inbodyResponseDto, memberResponseDto, exerciseResponseDto, healthInformationResponseDto);
        ResponseEntity<InbodyFeedbackResponseDto> responseEntity = exchangeInbodyData(dataMap);
        return responseEntity;
    }

    private Map<String, Object> createInbodyDataMap(UUID inbodyUuid, InbodyResponseDto inbodyResponseDto, MemberResponseDto memberResponseDto, ExerciseResponseDto exerciseResponseDto, Optional<HealthInformationResponseDto> healthInformationResponseDto) {
        DailyActivity activity = healthInformationResponseDto.map(HealthInformationResponseDto::getDailyActivity)
                .orElse(DailyActivity.NO_INFORMATION);
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
        dataMap.put("exercisePurpose", exerciseResponseDto.getExercisePurpose());
        dataMap.put("healthInformation", activity);
        return dataMap;
    }

    private ResponseEntity<InbodyFeedbackResponseDto> exchangeInbodyData(Map<String, Object> dataMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(dataMap, headers);
        ResponseEntity<InbodyFeedbackResponseDto> responseEntity = restTemplate.exchange("http://ai:5000/receive-inbody", HttpMethod.POST, requestEntity, InbodyFeedbackResponseDto.class);
        InbodyFeedbackResponseDto body = responseEntity.getBody();
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

}
