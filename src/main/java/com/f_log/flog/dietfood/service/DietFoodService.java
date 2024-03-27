package com.f_log.flog.dietfood.service;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.dietfood.util.roundUtil;
import com.f_log.flog.diet.repository.DietRepository;
import com.f_log.flog.dietfood.domain.DietFood;
import com.f_log.flog.dietfood.dto.CreateDietFoodRequest;
import com.f_log.flog.dietfood.dto.DietFoodResponseDto;
import com.f_log.flog.dietfood.dto.UpdateDietFoodRequest;
import com.f_log.flog.dietfood.repository.DietFoodRepository;
import com.f_log.flog.food.domain.Food;
import com.f_log.flog.food.repository.FoodRepository;
import com.f_log.flog.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DietFoodService {
    private final DietFoodRepository dietFoodRepository;
    private final DietRepository dietRepository;
    private final FoodRepository foodRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public DietFoodResponseDto createDietFood(CreateDietFoodRequest createDietFoodRequest) {
        // Find the Food entity
        Food food = foodRepository.findByFoodUuidAndIsDeletedFalse(createDietFoodRequest.getFoodUuid())
                .orElseThrow(() -> new EntityNotFoundException("Food not found with uuid: " + createDietFoodRequest.getFoodUuid()));

        // Find the Diet entity
        Diet diet = dietRepository.findById(createDietFoodRequest.getDietUuid())
                .orElseThrow(() -> new EntityNotFoundException("Diet not found with uuid: " + createDietFoodRequest.getDietUuid()));

        // Create DietFood entity
        DietFood dietFood = DietFood.builder()
                .diet(diet)
                .food(food)
                .quantity(createDietFoodRequest.getQuantity())
                .notes(createDietFoodRequest.getNotes())
                .foodName(food.getFoodName())
                .build();

        // Save the DietFood entity
        DietFood savedDietFood = dietFoodRepository.save(dietFood);

        // Calculate nutritional values based on quantity
        double quantity = createDietFoodRequest.getQuantity();
        double newCarbohydrates = roundUtil.roundToTwoDecimals(food.getCarbohydrate() * quantity);
        double newProtein = roundUtil.roundToTwoDecimals(food.getProtein() * quantity);
        double newFat = roundUtil.roundToTwoDecimals(food.getFat() * quantity);
        double newSodium = roundUtil.roundToTwoDecimals(food.getSodium() * quantity);
        double newCholesterol = roundUtil.roundToTwoDecimals(food.getCholesterol() * quantity);
        double newSugars = roundUtil.roundToTwoDecimals(food.getSugars() * quantity);
        double newCalories = roundUtil.roundToTwoDecimals(food.getCalories() * quantity);

        // Update Diet's nutritional information
        diet.addNutrients(newCarbohydrates, newProtein, newFat, newSodium, newCholesterol, newSugars, newCalories);
        dietRepository.save(diet);

        // Return DietFoodResponseDTO with relevant information
        return DietFoodResponseDto.builder()
                .dietfoodUuid(savedDietFood.getDietfoodUuid())
                .dietUuid(diet.getDietUuid())
                .foodUuid(savedDietFood.getFood().getFoodUuid())
                .quantity(savedDietFood.getQuantity())
                .foodName(food.getFoodName())
                .notes(savedDietFood.getNotes())
                .build();
    }

    @Transactional
    public DietFoodResponseDto findDietFood(UUID dietfoodUuid) {
        DietFood dietFood = dietFoodRepository.findByDietfoodUuidAndIsDeletedFalse(dietfoodUuid)
                .orElseThrow(() -> new EntityNotFoundException("DietFood not found with id: " + dietfoodUuid));

        return DietFoodResponseDto.builder()
                .dietfoodUuid(dietFood.getDietfoodUuid())
                .dietUuid(dietFood.getDiet().getDietUuid())
                .foodUuid(dietFood.getFood().getFoodUuid())
                .quantity(dietFood.getQuantity())
                .notes(dietFood.getNotes())
                .build();
    }

    @Transactional
    public DietFoodResponseDto updateDietFood(UUID dietfoodUuid, UpdateDietFoodRequest updateDietFoodRequest) {
        DietFood dietFood = dietFoodRepository.findByDietfoodUuidAndIsDeletedFalse(dietfoodUuid)
                .orElseThrow(() -> new EntityNotFoundException("DietFood not found with id: " + dietfoodUuid));

        // Retrieve old nutritional values before update
        double oldQuantity = dietFood.getQuantity();
        Food food = dietFood.getFood();
        Diet diet = dietFood.getDiet();
        double oldCarbohydrates = food.getCarbohydrate() * oldQuantity;
        double oldProtein = food.getProtein() * oldQuantity;
        double oldFat = food.getFat() * oldQuantity;
        double oldSodium = food.getSodium() * oldQuantity;
        double oldCholesterol = food.getCholesterol() * oldQuantity;
        double oldSugars = food.getSugars() * oldQuantity;
        double oldCalories = food.getCalories() * oldQuantity;

        // Update DietFood with new values
        dietFood.updateQuantity(updateDietFoodRequest.getQuantity());
        dietFood.updateNotes(updateDietFoodRequest.getNotes());

        // Save updated DietFood
        dietFood = dietFoodRepository.save(dietFood);

        // Calculate new nutritional values based on updated quantity
        double newQuantity = updateDietFoodRequest.getQuantity();
        double newCarbohydrates = roundUtil.roundToTwoDecimals(food.getCarbohydrate() * newQuantity);
        double newProtein = roundUtil.roundToTwoDecimals(food.getProtein() * newQuantity);
        double newFat = roundUtil.roundToTwoDecimals(food.getFat() * newQuantity);
        double newSodium = roundUtil.roundToTwoDecimals(food.getSodium() * newQuantity);
        double newCholesterol = roundUtil.roundToTwoDecimals(food.getCholesterol() * newQuantity);
        double newSugars = roundUtil.roundToTwoDecimals(food.getSugars() * newQuantity);
        double newCalories = roundUtil.roundToTwoDecimals(food.getCalories() * newQuantity);

        // Update Diet's nutritional information
        // Subtract old nutritional values
        diet.subtractNutrients(oldCarbohydrates, oldProtein, oldFat, oldSodium, oldCholesterol, oldSugars, oldCalories);
        // Add new nutritional values
        diet.addNutrients(newCarbohydrates, newProtein, newFat, newSodium, newCholesterol, newSugars, newCalories);
        // Save updated Diet
        dietRepository.save(diet);

        // Return updated DietFoodResponseDto
        return DietFoodResponseDto.builder()
                .dietfoodUuid(dietFood.getDietfoodUuid())
                .dietUuid(diet.getDietUuid())
                .foodUuid(food.getFoodUuid())
                .quantity(dietFood.getQuantity())
                .notes(dietFood.getNotes())
                .foodName(dietFood.getFoodName())
                .build();
    }

    @Transactional
    public void deleteDietFood(UUID dietfoodUuid) {
        DietFood dietFood = dietFoodRepository.findByDietfoodUuidAndIsDeletedFalse(dietfoodUuid)
                .orElseThrow(() -> new EntityNotFoundException("DietFood not found with id: " + dietfoodUuid));

        // Soft delete the DietFood
        dietFood.setDeleted();
        dietFoodRepository.save(dietFood); // Note: This assumes your repository respects the soft delete in fetch operations

        // Retrieve and update the associated Diet
        Diet diet = dietFood.getDiet();
        if (diet != null) { // Check if DietFood had an associated Diet
            diet.removeDietFood(dietFood); // This method updates the diet's nutritional info and removes the DietFood from the list
            dietRepository.save(diet);
        }
    }
}
