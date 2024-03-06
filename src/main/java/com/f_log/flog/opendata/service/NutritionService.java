package com.f_log.flog.opendata.service;

import com.f_log.flog.food.domain.Food;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Iterator;

import java.net.URI;
import java.util.List;

@Service
public class NutritionService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.key}")
    private String apiKey;

    public List<Food> getNutritionInfo(String pageNo, String numOfRows, String type, String foodName) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(apiUrl + "/" + apiKey + "/I2790/" + type + "/" + pageNo + "/" + numOfRows + "/DESC_KOR=" + foodName)
                .build()
                .encode()
                .toUri();
        System.out.println(uri);
        String response = restTemplate.getForObject(uri, String.class);
        saveResponseToFile(response);
        return extractFoodsFromResponse(response);
    }

    /**
     * JSON 응답에서 필요한 데이터를 추출하여 Food 객체 리스트로 반환합니다.
     *
     * @param response JSON 형태의 응답 문자열
     * @return 추출된 Food 객체 리스트
     */
    private List<Food> extractFoodsFromResponse(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode items = root.path("I2790").path("row");

            List<Food> foods = new ArrayList<>();
            if (items.isArray()) {
                for (JsonNode item : items) {
                    Food food = Food.builder()
                            .foodName(item.path("DESC_KOR").asText())
                            .servingSize(item.path("SERVING_SIZE").asInt())
                            .servingUnit(item.path("SERVING_UNIT").asInt())
                            .calories(item.path("NUTR_CONT1").asInt())
                            .carbohydrate(item.path("NUTR_CONT2").asInt())
                            .protein(item.path("NUTR_CONT3").asInt())
                            .fat(item.path("NUTR_CONT4").asInt())
                            .sugars(item.path("NUTR_CONT5").asInt())
                            .sodium(item.path("NUTR_CONT6").asInt())
                            .cholesterol(item.path("NUTR_CONT7").asInt())
                            .saturatedFat(item.path("NUTR_CONT8").asInt())
                            .transFat(item.path("NUTR_CONT9").asInt())
                            .build();
                    foods.add(food);
                }
            }
            return foods;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * JSON 응답을 파일로 저장합니다.
     *
     * @param response JSON 형태의 응답 문자열
     */
    private void saveResponseToFile(String response) {
        try {
            Path path = Path.of("nutrition_response.json");
            Files.deleteIfExists(path);
            Files.writeString(path, response, StandardOpenOption.CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}