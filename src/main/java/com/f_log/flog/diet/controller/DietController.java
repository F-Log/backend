package com.f_log.flog.diet.controller;

import com.f_log.flog.diet.dto.CreateDietRequest;
import com.f_log.flog.diet.dto.DietDto;
import com.f_log.flog.diet.dto.UpdateDietRequest;
import com.f_log.flog.diet.service.DietService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/diet")
@RequiredArgsConstructor
public class DietController {
    private final DietService dietService;

    @PostMapping("/new")
    public ResponseEntity<DietDto> createDiet(@RequestBody CreateDietRequest createDietRequest) {
        UUID uuid = dietService.saveDiet(createDietRequest);
        DietDto dietDto = dietService.findDietByUuid(uuid);
        return new ResponseEntity<>(dietDto, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<DietDto> findDietByUuid(@PathVariable UUID uuid) {
        DietDto dietDto = dietService.findDietByUuid(uuid);
        return ResponseEntity.ok(dietDto);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<DietDto> updateDiet(@PathVariable UUID uuid, @RequestBody UpdateDietRequest updateDietRequest) {
        boolean updated = dietService.updateDiet(uuid, updateDietRequest);
        if (updated) {
            DietDto updatedDiet = dietService.findDietByUuid(uuid);
            return ResponseEntity.ok(updatedDiet);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteDiet(@PathVariable UUID uuid) {
        boolean deleted = dietService.deleteDiet(uuid);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
