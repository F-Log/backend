package com.f_log.flog.dietfood.service;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.diet.repository.DietRepository;
import com.f_log.flog.dietfood.domain.DietFood;
import com.f_log.flog.dietfood.dto.DietFoodRequestDTO;
import com.f_log.flog.dietfood.dto.DietFoodResponseDTO;
import com.f_log.flog.dietfood.repository.DietFoodRepository;
import com.f_log.flog.food.domain.Food;
import com.f_log.flog.food.repository.FoodRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DietFoodService {
    private final DietFoodRepository dietFoodRepository;
    private final DietRepository dietRepository;
    private final FoodRepository foodRepository;

    @Transactional
    public DietFoodResponseDTO createDietFood(DietFoodRequestDTO requestDTO) {
        Diet diet = dietRepository.findByDietUuidAndIsDeletedFalse(requestDTO.getDietUuid())
                .orElseThrow(() -> new EntityNotFoundException("Diet not found with id: " + requestDTO.getDietUuid()));
        Food food = foodRepository.findByFoodUuidAndIsDeletedFalse(requestDTO.getFoodUuid())
                .orElseThrow(() -> new EntityNotFoundException("Food not found with id: " + requestDTO.getFoodUuid()));

        DietFood dietFood = DietFood.builder()
                .diet(diet)
                .food(food)
                .quantity(requestDTO.getQuantity())
                .notes(requestDTO.getNotes())
                .build();
        DietFood savedDietFood = dietFoodRepository.save(dietFood);

        return DietFoodResponseDTO.builder()
                .dietfoodUuid(savedDietFood.getDietfoodUuid())
                .dietUuid(savedDietFood.getDiet().getDietUuid())
                .foodUuid(savedDietFood.getFood().getFoodUuid())
                .quantity(savedDietFood.getQuantity())
                .notes(savedDietFood.getNotes())
                .build();
    }

    @Transactional
    public DietFoodResponseDTO findDietFood(UUID dietfoodUuid) {
        DietFood dietFood = dietFoodRepository.findByDietfoodUuidAndIsDeletedFalse(dietfoodUuid)
                .orElseThrow(() -> new EntityNotFoundException("DietFood not found with id: " + dietfoodUuid));

        return DietFoodResponseDTO.builder()
                .dietfoodUuid(dietFood.getDietfoodUuid())
                .dietUuid(dietFood.getDiet().getDietUuid())
                .foodUuid(dietFood.getFood().getFoodUuid())
                .quantity(dietFood.getQuantity())
                .notes(dietFood.getNotes())
                .build();
    }

    @Transactional
    public DietFoodResponseDTO updateDietFood(UUID dietfoodUuid, DietFoodRequestDTO requestDTO) {
        DietFood dietFood = dietFoodRepository.findByDietfoodUuidAndIsDeletedFalse(dietfoodUuid)
                .orElseThrow(() -> new EntityNotFoundException("DietFood not found with id: " + dietfoodUuid));

        dietFood.updateQuantity(requestDTO.getQuantity());
        dietFood.updateNotes(requestDTO.getNotes());

        dietFood = dietFoodRepository.save(dietFood);

        return DietFoodResponseDTO.builder()
                .dietfoodUuid(dietFood.getDietfoodUuid())
                .dietUuid(dietFood.getDiet().getDietUuid())
                .foodUuid(dietFood.getFood().getFoodUuid())
                .quantity(dietFood.getQuantity())
                .notes(dietFood.getNotes())
                .build();
    }


    @Transactional
    public void deleteDietFood(UUID dietfoodUuid) {
        DietFood dietFood = dietFoodRepository.findByDietfoodUuidAndIsDeletedFalse(dietfoodUuid)
                .orElseThrow(() -> new EntityNotFoundException("DietFood not found with id: " + dietfoodUuid));
        dietFood.setDeleted();
        dietFoodRepository.save(dietFood);
    }
}
