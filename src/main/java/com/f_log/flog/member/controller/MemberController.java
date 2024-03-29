package com.f_log.flog.member.controller;

import com.f_log.flog.member.dto.MemberLoginResponseDto;
import com.f_log.flog.member.dto.MemberRequestDto;
import com.f_log.flog.member.dto.MemberResponseDto;
import com.f_log.flog.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // ISSUE: RESPONSE ENTITY SHOWN
    @PostMapping("/new")
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberRequestDto memberRequestDto) {
        UUID uuid = memberService.saveMember(memberRequestDto);
        MemberResponseDto memberResponseDto = memberService.getMemberByUuid(uuid);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponseDto> login(@RequestBody MemberRequestDto memberRequestDto, HttpSession session) {
        MemberLoginResponseDto memberLoginResponseDto = memberService.login(
                memberRequestDto.getLoginId(), memberRequestDto.getPassword(), session);
        return ResponseEntity.ok(memberLoginResponseDto);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<MemberResponseDto> getMemberByUuid(@PathVariable UUID uuid) {
        MemberResponseDto memberResponseDto = memberService.getMemberByUuid(uuid);
        if (memberResponseDto != null) {
            return ResponseEntity.ok(memberResponseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        List<MemberResponseDto> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<MemberResponseDto> updateMember(@PathVariable UUID uuid, @RequestBody MemberRequestDto memberRequestDto) {
        boolean updated = memberService.updateMember(uuid, memberRequestDto);
        if (updated) {
            MemberResponseDto updatedMember = memberService.getMemberByUuid(uuid);
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteMember(@PathVariable UUID uuid) {
        boolean deleted = memberService.deleteMember(uuid);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
