package com.f_log.flog.controller;

import com.f_log.flog.dto.MemberRequestDto;
import com.f_log.flog.dto.MemberResponseDto;
import com.f_log.flog.service.MemberService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> createMember(@RequestBody MemberRequestDto memberRequestDto) {
        UUID uuid = memberService.saveMember(memberRequestDto);
        return ResponseEntity.ok().body("Member created with UUID: " + uuid);
    }
}
