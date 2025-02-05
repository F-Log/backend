package com.f_log.flog.csvbatch.readerprocessorwriter;

import com.f_log.flog.csvbatch.dto.FoodRawDTO;
import com.f_log.flog.food.domain.Food;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessorConfig {

    @Bean
    public ItemProcessor<FoodRawDTO, Food> foodItemProcessor() {
        return foodRaw -> {
            // Food.builder()를 사용하여 필요한 필드를 변환 후 Food 객체를 생성합니다.
            return Food.builder()
                    .foodName(foodRaw.getFoodName())
                    .makerName(foodRaw.getMakerName())
                    .calories(parseInt(foodRaw.getCalories(), "에너지(kcal)"))
                    .protein(parseDouble(foodRaw.getProtein(), "단백질(g)"))
                    .fat(parseDouble(foodRaw.getFat(), "지방(g)"))
                    .carbohydrate(parseDouble(foodRaw.getCarbohydrate(), "탄수화물(g)"))
                    .sugars(parseDouble(foodRaw.getSugars(), "당류(g)"))
                    .sodium(parseInt(foodRaw.getSodium(), "나트륨(mg)"))
                    .cholesterol(parseDouble(foodRaw.getCholesterol(), "콜레스테롤(mg)"))
                    .saturatedFat(parseDouble(foodRaw.getSaturatedFat(), "포화지방산(g)"))
                    .transFat(parseDouble(foodRaw.getTransFat(), "트랜스지방산(g)"))
                    .servingUnit(extractIntFromString(foodRaw.getServingUnit()))
                    .build();
        };
    }

    private int parseInt(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) return 0;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " 파싱 중 오류 발생: " + value, e);
        }
    }

    private double parseDouble(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) return 0.0;
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " 파싱 중 오류 발생: " + value, e);
        }
    }

    private int extractIntFromString(String str) {
        if (str == null) return 0;
        String numberOnly = str.replaceAll("[^0-9]", "");
        return numberOnly.isEmpty() ? 0 : Integer.parseInt(numberOnly);
    }
}