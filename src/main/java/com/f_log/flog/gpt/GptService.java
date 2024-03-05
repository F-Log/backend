package com.f_log.flog.gpt;

import com.f_log.flog.diet.dto.DietDto;
import com.f_log.flog.dietfeedback.dto.DietFeedbackDto;
import com.f_log.flog.inbody.dto.InbodyResponseDto;
import com.f_log.flog.inbodyfeedback.dto.InbodyFeedbackResponseDto;
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

    public ResponseEntity<DietFeedbackDto> createDietFeedback(UUID dietUuid, DietDto dietDto, MemberResponseDto memberResponseDto, InbodyResponseDto inbodyResponseDto) {
        Map<String, Object> dataMap = createDietDataMap(dietUuid, dietDto, memberResponseDto, inbodyResponseDto);
        ResponseEntity<DietFeedbackDto> responseEntity = exchangeDietData(dataMap);
        return responseEntity;
    }

    private Map<String, Object> createDietDataMap(UUID dietUuid, DietDto dietDto, MemberResponseDto memberResponseDto, InbodyResponseDto inbodyResponseDto) {
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

    private ResponseEntity<DietFeedbackDto> exchangeDietData(Map<String, Object> dataMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(dataMap, headers);
        ResponseEntity<DietFeedbackDto> responseEntity = restTemplate.exchange("http://127.0.0.1:5000/receive-diet", HttpMethod.POST, requestEntity, DietFeedbackDto.class);
        DietFeedbackDto body = responseEntity.getBody();
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    public ResponseEntity<InbodyFeedbackResponseDto> createInbodyFeedback(UUID inbodyUuid, InbodyResponseDto inbodyResponseDto, MemberResponseDto memberResponseDto) {
        Map<String, Object> dataMap = createInbodyDataMap(inbodyUuid, inbodyResponseDto, memberResponseDto);
        ResponseEntity<InbodyFeedbackResponseDto> responseEntity = exchangeInbodyData(dataMap);
        return responseEntity;
    }

    private Map<String, Object> createInbodyDataMap(UUID inbodyUuid, InbodyResponseDto inbodyResponseDto, MemberResponseDto memberResponseDto) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("inbodyUuid", inbodyUuid);
        dataMap.put("height", inbodyResponseDto.getHeight());
        dataMap.put("bodyWeight", inbodyResponseDto.getBodyWeight());
        dataMap.put("fatMass", inbodyResponseDto.getFatMass());
        dataMap.put("basalMetabolicRate", inbodyResponseDto.getBasalMetabolicRate());
        dataMap.put("bodyFatPercentage", inbodyResponseDto.getBodyFatPercentage());
        dataMap.put("muscleMass", inbodyResponseDto.getMuscleMass());
        dataMap.put("gender", memberResponseDto.getGender());
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
