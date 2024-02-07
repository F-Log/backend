package com.f_log.flog.controller;

import com.f_log.flog.dto.MemberRequestDto;
import com.f_log.flog.dto.MemberResponseDto;
import com.f_log.flog.service.MemberService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberRequestDto memberRequestDto) {
        UUID uuid = memberService.saveMember(memberRequestDto);
        MemberResponseDto memberResponseDto = memberService.getMemberByUuid(uuid);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.CREATED);
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
