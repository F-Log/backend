package com.f_log.flog.gpt;

import com.f_log.flog.diet.dto.DietDto;
import com.f_log.flog.diet.service.DietService;
import com.f_log.flog.dietfeedback.dto.DietFeedbackDto;
import com.f_log.flog.inbody.dto.InbodyResponseDto;
import com.f_log.flog.inbody.service.InbodyService;
import com.f_log.flog.member.dto.MemberResponseDto;
import com.f_log.flog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/gpt")
@RequiredArgsConstructor
public class GptController {

    private final DietService dietService;
    private final MemberService memberService;
    private final InbodyService inbodyService;
    private final GptService gptService;

    @PostMapping("/diet-feedback")
    public ResponseEntity<DietFeedbackDto> sendDataToFlask(@RequestBody UUID dietUuid) {
        DietDto foundDiet = dietService.getDietByUuid(dietUuid);
        UUID memberUuid = foundDiet.getMemberUuid();
        MemberResponseDto foundMember = memberService.getMemberByUuid(memberUuid);
        InbodyResponseDto foundInbody = inbodyService.getLatestInbodyOfMember(memberUuid);
        return gptService.sendDataToFlask(dietUuid, foundDiet, foundMember, foundInbody);
    }
}
