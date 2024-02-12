package com.f_log.flog.healthinformation.controller;

import com.f_log.flog.healthinformation.domain.DailyActivity;
import com.f_log.flog.healthinformation.domain.HealthInformation;
import com.f_log.flog.healthinformation.service.HealthInformationService;
import com.f_log.flog.member.domain.Member;
import com.f_log.flog.member.repository.MemberRepository;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health-information")
public class HealthInformationController {
    private final HealthInformationService healthInformationService;

    @Autowired
    public HealthInformationController(HealthInformationService healthInformationService) {
        this.healthInformationService = healthInformationService;
    }

    @PostMapping("/new")
    public ResponseEntity<HealthInformation> createHealthInformation(@RequestBody Map<String, String> request) {
        UUID memberUuid = UUID.fromString(request.get("memberUuid"));
        HealthInformation healthInformation = new HealthInformation();
        healthInformation.changeDailyActivity(DailyActivity.valueOf(request.get("dailyActivity")));
        HealthInformation savedHealthInformation = healthInformationService.saveHealthInformation(healthInformation, memberUuid);
        return new ResponseEntity<>(savedHealthInformation, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<HealthInformation> getHealthInformationByMemberUuid(@PathVariable UUID uuid) {
        HealthInformation healthInformation = healthInformationService.getHealthInformationByMemberUuid(uuid);
        if (healthInformation != null) {
            return ResponseEntity.ok(healthInformation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<HealthInformation> updateHealthInformationByMemberUuid(@PathVariable UUID uuid, @RequestBody HealthInformation healthInformation) {
        HealthInformation updatedHealthInformation = healthInformationService.updateHealthInformationByMemberUuid(uuid, healthInformation);
        if (updatedHealthInformation != null) {
            return ResponseEntity.ok(updatedHealthInformation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteHealthInformationByMemberUuid(@PathVariable UUID uuid) {
        boolean deleted = healthInformationService.deleteHealthInformationByMemberUuid(uuid);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}