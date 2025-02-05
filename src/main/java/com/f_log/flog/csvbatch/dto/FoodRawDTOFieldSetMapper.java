package com.f_log.flog.csvbatch.dto;

import com.f_log.flog.food.domain.Food;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class FoodRawDTOFieldSetMapper implements FieldSetMapper<FoodRawDTO> {

    @Override
    public FoodRawDTO mapFieldSet(FieldSet fieldSet) throws BindException {
        return FoodRawDTO.builder()
                .foodName(fieldSet.readString("foodName"))
                .calories(fieldSet.readString("calories"))
                .protein(fieldSet.readString("protein"))
                .fat(fieldSet.readString("fat"))
                .carbohydrate(fieldSet.readString("carbohydrate"))
                .sugars(fieldSet.readString("sugars"))
                .sodium(fieldSet.readString("sodium"))
                .cholesterol(fieldSet.readString("cholesterol"))
                .saturatedFat(fieldSet.readString("saturatedFat"))
                .transFat(fieldSet.readString("transFat"))
                .servingUnit(fieldSet.readString("servingUnit"))
                .makerName(fieldSet.readString("makerName"))
                .build();
    }
}
