package com.f_log.flog.gpt;

import com.f_log.flog.diet.dto.DietDto;
import com.f_log.flog.diet.service.DietService;
import com.f_log.flog.dietfeedback.dto.DietFeedbackDto;
import com.f_log.flog.exercise.dto.ExerciseResponseDto;
import com.f_log.flog.exercise.service.ExerciseService;
import com.f_log.flog.inbody.dto.InbodyResponseDto;
import com.f_log.flog.inbody.service.InbodyService;
import com.f_log.flog.inbodyfeedback.dto.InbodyFeedbackResponseDto;
import com.f_log.flog.member.dto.MemberResponseDto;
import com.f_log.flog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/diet-feedback")
    public ResponseEntity<DietFeedbackDto> createDietFeedback(@RequestBody UUID dietUuid) {
        DietDto foundDiet = dietService.getDietByUuid(dietUuid);
        UUID memberUuid = foundDiet.getMemberUuid();
        MemberResponseDto foundMember = memberService.getMemberByUuid(memberUuid);
        InbodyResponseDto foundInbody = inbodyService.getLatestInbodyOfMember(memberUuid);
        ExerciseResponseDto foundExercise = exerciseService.getExercise(memberUuid);
        return gptService.createDietFeedback(dietUuid, foundDiet, foundMember, foundInbody, foundExercise);
    }

    @PostMapping("/inbody-feedback")
    public ResponseEntity<InbodyFeedbackResponseDto> createInbodyFeedback(@RequestBody UUID inbodyUuid) {
        InbodyResponseDto foundInbody = inbodyService.getInbodyByUuid(inbodyUuid);
        MemberResponseDto foundMember = memberService.getMemberByUuid(foundInbody.getMemberUuid());
        ExerciseResponseDto foundExercise = exerciseService.getExercise(foundMember.getUuid());
        return gptService.createInbodyFeedback(inbodyUuid, foundInbody, foundMember, foundExercise);
    }
}
