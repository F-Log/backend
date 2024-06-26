package com.f_log.flog.diet.controller;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.diet.domain.MealType;
import com.f_log.flog.diet.dto.*;
import com.f_log.flog.dietfood.dto.DietFoodResponseDto;

import com.f_log.flog.diet.service.DietService;
import com.f_log.flog.member.dto.MemberResponseDto;
import com.f_log.flog.member.service.MemberService;
import com.f_log.flog.s3bucket.dto.FileUploadResponse;
import com.f_log.flog.s3bucket.service.S3Uploader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/diet")
@RequiredArgsConstructor
@Slf4j
public class DietController {
    private final DietService dietService;
    private final MemberService memberService;
    private final S3Uploader s3Uploader;

    @PostMapping("/new")
    public ResponseEntity<DietDto> createDiet(@RequestBody CreateDietRequest request) {
        DietDto dietDto = dietService.createDiet(request);
        return new ResponseEntity<>(dietDto, HttpStatus.CREATED);
    }

    @PostMapping("/imageUrl/{dietUuid}")
    public ResponseEntity<?> uploadDietImage(
            @PathVariable("dietUuid") UUID dietUuid,
            @RequestParam("image") MultipartFile multipartFile) {
        try {
            FileUploadResponse response = s3Uploader.uploadFiles(dietUuid, multipartFile, "diet");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error uploading file to S3", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            log.error("IO Exception while uploading file to S3", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/updateImageUrl/{dietUuid}")
    public ResponseEntity<?> updateDietImageUrl(@PathVariable UUID dietUuid, @RequestParam("image") MultipartFile image, @RequestParam("dirName") String dirName) {
        try {
            FileUploadResponse response = s3Uploader.updateDietImageUrl(dietUuid, image, dirName);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            log.error("Error updating diet image URL: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating diet image URL: " + e.getMessage());
        } catch (IllegalStateException | IllegalArgumentException e) {
            log.error("Error updating diet image URL: ", e);
            return ResponseEntity.badRequest().body("Error updating diet image URL: " + e.getMessage());
        }
    }

    @GetMapping("/date-memberUuid")
    public ResponseEntity<List<DietDetailDto>> getDietDetails(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("memberUuid") UUID memberUuid,
            @RequestParam("mealType") MealType mealType) {

        List<Diet> diets = dietService.findDietsByDateAndMemberUuidAndMealType(date, memberUuid, mealType);

        List<DietDetailDto> dietDetails = diets.stream().map(diet -> {
            List<DietFoodResponseDto> dietFoodDtos = diet.getDietFoods().stream()
                    .map(dietFood -> DietFoodResponseDto.builder()
                            .dietfoodUuid(dietFood.getDietfoodUuid())
                            .dietUuid(dietFood.getDiet().getDietUuid())
                            .foodUuid(dietFood.getFood().getFoodUuid())
                            .quantity(dietFood.getQuantity())
                            .foodName(dietFood.getFoodName())
                            .notes(dietFood.getNotes())
                            .build())
                    .collect(Collectors.toList());

            // DietDetailDto를 빌드합니다.
            return DietDetailDto.builder()
                    .dietUuid(diet.getDietUuid())
                    .memberUuid(diet.getMember().getUuid())
                    .mealDate(diet.getMealDate())
                    .mealType(diet.getMealType())
                    .totalCalories(diet.getTotalCalories())
                    .totalCarbohydrate(diet.getTotalCarbohydrate())
                    .totalProtein(diet.getTotalProtein())
                    .totalFat(diet.getTotalFat())
                    .dietFoods(dietFoodDtos)
                    .build();
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dietDetails);
    }


    @GetMapping("/{dietUuid}")
    public ResponseEntity<DietDto> getDiet(@PathVariable UUID dietUuid) {
        DietDto dietDto = dietService.getDietDtoWithNonDeletedDietFoods(dietUuid);
        return ResponseEntity.ok(dietDto);
    }

    @PutMapping("/{dietUuid}")
    public ResponseEntity<DietDto> updateDiet(@PathVariable UUID dietUuid, @RequestBody UpdateDietRequest request) {
        DietDto dietDto = dietService.updateDiet(dietUuid, request);
        return ResponseEntity.ok(dietDto);
    }

    @DeleteMapping("/{dietUuid}")
    public ResponseEntity<Void> deleteDiet(@PathVariable UUID dietUuid) {
        dietService.deleteDiet(dietUuid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/daily-intake")
    public ResponseEntity<DailyIntakeDto> getTotalIntakeForDay(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                               @RequestParam("memberUuid") UUID memberUuid) {
        MemberResponseDto memberDto = memberService.getMemberByUuid(memberUuid);

        DailyIntakeDto dailyIntake = dietService.getTotalIntakeForDay(date, memberDto);
        return ResponseEntity.ok(dailyIntake);
    }

    @PostMapping("/register")
    public ResponseEntity<DietDto> registerDiet(@RequestBody RegisterDietRequest request) {
        DietDto dietDto = dietService.registerDiet(request);
        return new ResponseEntity<>(dietDto, HttpStatus.CREATED);
    }
}