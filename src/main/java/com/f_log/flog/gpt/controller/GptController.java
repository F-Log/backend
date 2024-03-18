package com.f_log.flog.gpt.controller;

import com.f_log.flog.allergy.dto.AllergyDto;
import com.f_log.flog.allergy.service.AllergyService;
import com.f_log.flog.dailydietfeedback.dto.DailyDietFeedbackDto;
import com.f_log.flog.diet.dto.DailyIntakeDto;
import com.f_log.flog.diet.dto.DietDto;
import com.f_log.flog.diet.service.DietService;
import com.f_log.flog.dietfeedback.dto.DietFeedbackDto;
import com.f_log.flog.exercise.dto.ExerciseResponseDto;
import com.f_log.flog.exercise.service.ExerciseService;
import com.f_log.flog.gpt.dto.CreateDailyDietFeedbackRequest;
import com.f_log.flog.gpt.dto.CreateDietFeedbackRequest;
import com.f_log.flog.gpt.dto.InbodyUuidRequest;
import com.f_log.flog.gpt.service.GptService;
import com.f_log.flog.healthinformation.dto.HealthInformationResponseDto;
import com.f_log.flog.healthinformation.service.HealthInformationService;
import com.f_log.flog.inbody.dto.InbodyResponseDto;
import com.f_log.flog.inbody.service.InbodyService;
import com.f_log.flog.inbodyfeedback.dto.InbodyFeedbackResponseDto;
import com.f_log.flog.member.dto.MemberResponseDto;
import com.f_log.flog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/gpt")
@RequiredArgsConstructor
public class GptController {

    private final DietService dietService;
    private final MemberService memberService;
    private final InbodyService inbodyService;
    private final GptService gptService;
    private final ExerciseService exerciseService;
    private final HealthInformationService healthInformationService;
    private final AllergyService allergyService;

    // 특정 식단에 대한 피드백
    @PostMapping("/diet-feedback")
    public ResponseEntity<DietFeedbackDto> createDietFeedback(@RequestBody CreateDietFeedbackRequest request) {
        List<AllergyDto> foundAllergies = allergyService.findAllergiesByMemberUuid(request.getMemberUuid());
        DietDto foundDiet = dietService.getDietByUuid(request.getDietUuid());
        return gptService.createDietFeedback(foundDiet, foundAllergies);
    }

    // 하루 총 영양섭취 피드백
    @PostMapping("/daily-diet-feedback")
    public ResponseEntity<DailyDietFeedbackDto> createDailyDietFeedback(@RequestBody CreateDailyDietFeedbackRequest request) {
        UUID memberUuid = request.getMemberUuid();
        LocalDate mealDate = request.getDate();

        MemberResponseDto foundMember = memberService.getMemberByUuid(memberUuid);
        DailyIntakeDto totalIntakeForDay = dietService.getTotalIntakeForDay(mealDate, foundMember);
        InbodyResponseDto foundInbody = inbodyService.getLatestInbodyOfMember(memberUuid);
        ExerciseResponseDto foundExercise = exerciseService.getExercise(memberUuid);
        return gptService.createDailyDietFeedback(mealDate, foundMember, totalIntakeForDay, foundInbody, foundExercise);
    }

    @PostMapping("/inbody-feedback")
    public ResponseEntity<InbodyFeedbackResponseDto> createInbodyFeedback(@RequestBody InbodyUuidRequest request) {
        UUID inbodyUuid = request.getInbodyUuid();
        InbodyResponseDto foundInbody = inbodyService.getInbodyByUuid(inbodyUuid);
        MemberResponseDto foundMember = memberService.getMemberByUuid(foundInbody.getMemberUuid());
        ExerciseResponseDto foundExercise = exerciseService.getExercise(foundMember.getUuid());
        Optional<HealthInformationResponseDto> foundHealthInformation = healthInformationService.getHealthInformationByMemberUuid(foundMember.getUuid());
        return gptService.createInbodyFeedback(inbodyUuid, foundInbody, foundMember, foundExercise, foundHealthInformation);
    }

}
