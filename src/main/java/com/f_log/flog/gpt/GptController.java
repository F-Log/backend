package com.f_log.flog.gpt;

import com.f_log.flog.diet.dto.DietDto;
import com.f_log.flog.diet.service.DietService;
import com.f_log.flog.inbody.dto.InbodyResponseDto;
import com.f_log.flog.inbody.service.InbodyService;
import com.f_log.flog.member.domain.Gender;
import com.f_log.flog.member.dto.MemberResponseDto;
import com.f_log.flog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/gpt")
@RequiredArgsConstructor
public class GptController {

    private final DietService dietService;
    private final MemberService memberService;
    private final RestTemplate restTemplate;
    private final InbodyService inbodyService;

    @PostMapping("/send-data-to-flask")
    public ResponseEntity<String> sendDataToFlask(@RequestBody UUID dietUuid) {
        DietDto foundDiet = dietService.getDietByUuid(dietUuid);
        UUID memberUuid = foundDiet.getMemberUuid();
        MemberResponseDto foundMember = memberService.getMemberByUuid(memberUuid);
        InbodyResponseDto foundInbody = inbodyService.getLatestInbodyOfMember(memberUuid);

        // Extract required fields
        int totalCarbohydrate = foundDiet.getTotalCarbohydrate();
        int totalProtein = foundDiet.getTotalProtein();
        int totalSodium = foundDiet.getTotalSodium();
        int totalFat = foundDiet.getTotalFat();
        int totalCholesterol = foundDiet.getTotalCholesterol();
        Gender gender = foundMember.getGender();
        float height = foundInbody.getHeight();
        float bodyWeight = foundInbody.getBodyWeight();
        float fatMass = foundInbody.getFatMass();
        float basalMetabolicRate = foundInbody.getBasalMetabolicRate();
        float bodyFatPercentage = foundInbody.getBodyFatPercentage();
        float muscleMass = foundInbody.getMuscleMass();


        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("totalCarbohydrate", totalCarbohydrate);
        jsonMap.put("totalProtein", totalProtein);
        jsonMap.put("totalSodium", totalSodium);
        jsonMap.put("totalFat", totalFat);
        jsonMap.put("totalCholesterol", totalCholesterol);
        jsonMap.put("gender", gender);
        jsonMap.put("height", height);
        jsonMap.put("bodyWeight", bodyWeight);
        jsonMap.put("fatMass", fatMass);
        jsonMap.put("basalMetabolicRate", basalMetabolicRate);
        jsonMap.put("bodyFatPercentage", bodyFatPercentage);
        jsonMap.put("muscleMass", muscleMass);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request entity
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(jsonMap, headers);

        // Send data to Flask server
        restTemplate.exchange(
                "http://127.0.0.1:5000/receive-diet",
                HttpMethod.POST,
                requestEntity,
                Void.class);

        return ResponseEntity.ok().build();
    }
}
