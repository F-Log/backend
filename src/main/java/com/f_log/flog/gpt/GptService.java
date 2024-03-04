package com.f_log.flog.gpt;

import com.f_log.flog.diet.dto.DietDto;
import com.f_log.flog.dietfeedback.dto.DietFeedbackDto;
import com.f_log.flog.inbody.dto.InbodyResponseDto;
import com.f_log.flog.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GptService {

    private final RestTemplate restTemplate;

    public ResponseEntity<DietFeedbackDto> sendDataToFlask(UUID dietUuid, DietDto dietDto, MemberResponseDto memberResponseDto, InbodyResponseDto inbodyResponseDto) {
        Map<String, Object> dataMap = createDataMap(dietUuid, dietDto, memberResponseDto, inbodyResponseDto);
        ResponseEntity<DietFeedbackDto> responseEntity = sendToFlaskServer(dataMap);
        return responseEntity;
    }

    private Map<String, Object> createDataMap(UUID dietUuid, DietDto dietDto, MemberResponseDto memberResponseDto, InbodyResponseDto inbodyResponseDto) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("dietUuid", dietUuid);
        dataMap.put("totalCarbohydrate", dietDto.getTotalCarbohydrate());
        dataMap.put("totalProtein", dietDto.getTotalProtein());
        dataMap.put("totalSodium", dietDto.getTotalSodium());
        dataMap.put("totalFat", dietDto.getTotalFat());
        dataMap.put("totalCholesterol", dietDto.getTotalCholesterol());
        dataMap.put("gender", memberResponseDto.getGender());
        dataMap.put("height", inbodyResponseDto.getHeight());
        dataMap.put("bodyWeight", inbodyResponseDto.getBodyWeight());
        dataMap.put("fatMass", inbodyResponseDto.getFatMass());
        dataMap.put("basalMetabolicRate", inbodyResponseDto.getBasalMetabolicRate());
        dataMap.put("bodyFatPercentage", inbodyResponseDto.getBodyFatPercentage());
        dataMap.put("muscleMass", inbodyResponseDto.getMuscleMass());
        return dataMap;
    }

    private ResponseEntity<DietFeedbackDto> sendToFlaskServer(Map<String, Object> dataMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(dataMap, headers);
        ResponseEntity<DietFeedbackDto> responseEntity = restTemplate.exchange("http://127.0.0.1:5000/receive-diet", HttpMethod.POST, requestEntity, DietFeedbackDto.class);
        DietFeedbackDto body = responseEntity.getBody();
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }
}
