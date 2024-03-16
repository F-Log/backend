package com.f_log.flog.opendata.service;

import com.f_log.flog.food.domain.Food;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class NutritionService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.key}")
    private String apiKey;

    public List<Food> getNutritionInfo(String type, String foodName) {
        List<Food> allFoods = new ArrayList<>();
        int pageNo = 1;
        final int rowsPerPage = 1000; // 한 번에 요청할 데이터 수
        String response = null; // 응답을 저장할 변수 초기화
        while (true) {
            // 페이지 번호와 종료 번호를 사용하여 요청 URL 구성
            URI uri = UriComponentsBuilder
                    .fromHttpUrl(apiUrl + "/" + apiKey + "/I2790/" + type + "/" + pageNo + "/" + (pageNo + rowsPerPage - 1) + "/DESC_KOR=" + foodName)
                    .build()
                    .encode()
                    .toUri();
            response = restTemplate.getForObject(uri, String.class); // 응답 저장
            System.out.println(uri);

            if (response == null || response.contains("\"total_count\":\"0\"")) {
                break; // 더 이상 데이터가 없으면 반복 종료
            }

            List<Food> foods = extractFoodsFromResponse(response);
            if (!foods.isEmpty()) {
                allFoods.addAll(foods);
                pageNo += rowsPerPage; // 다음 요청을 위해 페이지 번호를 증가
            } else {
                break; // 데이터가 비어있으면 반복 종료
            }
        }

        processAndDeleteResponseFile(response);
        return allFoods;
    }

//    public List<Food> getNutritionInfo(String pageNo, String numOfRows, String type, String foodName) {
//        URI uri = UriComponentsBuilder
//                .fromHttpUrl(apiUrl + "/" + apiKey + "/I2790/" + type + "/" + pageNo + "/" + numOfRows + "/DESC_KOR=" + foodName)
//                .build()
//                .encode()
//                .toUri();
//        String response = restTemplate.getForObject(uri, String.class);
//        saveResponseToFile(response);
//        return extractFoodsFromResponse(response);
//    }

    /**
     * JSON 응답을 파일로 저장한 뒤 파일을 삭제합니다.
     *
     * @param response JSON 형태의 응답 문자열
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
                            .makerName(item.path("MAKER_NAME").asText())
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
    private void processAndDeleteResponseFile(String response) {
        try {
            Path path = Path.of("nutrition_response.json");
            if (Files.exists(path)) {
                Files.delete(path);
                System.out.println("Response file deleted successfully.");
            }
            Files.writeString(path, response, StandardOpenOption.CREATE);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to delete or write response file.");
        }
    }


}