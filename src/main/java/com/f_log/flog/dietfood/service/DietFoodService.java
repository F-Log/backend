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
        Diet diet = dietRepository.findById(requestDTO.getDietId())
                .orElseThrow(() -> new EntityNotFoundException("Diet not found with id: " + requestDTO.getDietId()));
        Food food = foodRepository.findById(requestDTO.getFoodId())
                .orElseThrow(() -> new EntityNotFoundException("Food not found with id: " + requestDTO.getFoodId()));

        DietFood dietFood = DietFood.builder()
                .diet(diet)
                .food(food)
                .quantity(requestDTO.getQuantity())
                .notes(requestDTO.getNotes())
                .build();
        DietFood savedDietFood = dietFoodRepository.save(dietFood);

        return DietFoodResponseDTO.builder()
                .id(savedDietFood.getId())
                .dietId(savedDietFood.getDiet().getId())
                .foodId(savedDietFood.getFood().getId())
                .quantity(savedDietFood.getQuantity())
                .notes(savedDietFood.getNotes())
                .build();
    }

    @Transactional
    public DietFoodResponseDTO findDietFood(Long id) {
        DietFood dietFood = dietFoodRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("DietFood not found with id: " + id));

        return DietFoodResponseDTO.builder()
                .id(dietFood.getId())
                .dietId(dietFood.getDiet().getId())
                .foodId(dietFood.getFood().getId())
                .quantity(dietFood.getQuantity())
                .notes(dietFood.getNotes())
                .build();
    }

    @Transactional
    public DietFoodResponseDTO updateDietFood(Long id, DietFoodRequestDTO requestDTO) {
        DietFood dietFood = dietFoodRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("DietFood not found with id: " + id));

        dietFood.updateQuantity(requestDTO.getQuantity());
        dietFood.updateNotes(requestDTO.getNotes());

        dietFood = dietFoodRepository.save(dietFood);

        return DietFoodResponseDTO.builder()
                .id(dietFood.getId())
                .dietId(dietFood.getDiet().getId())
                .foodId(dietFood.getFood().getId())
                .quantity(dietFood.getQuantity())
                .notes(dietFood.getNotes())
                .build();
    }


    @Transactional
    public void deleteDietFood(Long id) {
        DietFood dietFood = dietFoodRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("DietFood not found with id: " + id));
        dietFood.setDeleted();
        dietFoodRepository.save(dietFood);
    }
}
