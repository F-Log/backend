package com.f_log.flog.allergy.controller;

import com.f_log.flog.allergy.dto.AllergyDto;
import com.f_log.flog.allergy.dto.AllergyRequest;
import com.f_log.flog.allergy.service.AllergyService;
import com.f_log.flog.member.dto.MemberResponseDto;
import com.f_log.flog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/allergy")
@RequiredArgsConstructor
public class AllergyController {
    private final AllergyService allergyService;
    private final MemberService memberService;

    @PostMapping("/{memberUuid}")
    public ResponseEntity<AllergyDto> createAllergy(@PathVariable UUID memberUuid, @RequestBody AllergyRequest request) {
        MemberResponseDto foundMember = memberService.getMemberByUuid(memberUuid);
        if (foundMember != null) {
            AllergyDto created = allergyService.createAllergy(foundMember.getUuid(), request);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{allergyId}")
    public ResponseEntity<AllergyDto> getAllergy(@PathVariable Long allergyId) {
        AllergyDto found = allergyService.findAllergy(allergyId);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PutMapping("/{allergyId}")
    public ResponseEntity<AllergyDto> updateAllergy(@PathVariable Long allergyId, @RequestBody AllergyRequest request) {
        boolean updated = allergyService.updateAllergy(allergyId, request);
        if (updated) {
            AllergyDto updatedAllergy = allergyService.findAllergy(allergyId);
            return ResponseEntity.ok(updatedAllergy);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{allergyId}")
    public ResponseEntity<Void> deleteAllergy(@PathVariable Long allergyId) {
        boolean deleted = allergyService.deleteAllergy(allergyId);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
