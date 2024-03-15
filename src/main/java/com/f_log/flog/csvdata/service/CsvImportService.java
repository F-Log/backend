package com.f_log.flog.csvdata.service;

import com.f_log.flog.food.domain.Food;
import com.f_log.flog.food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvImportService {

    @Autowired
    private FoodService foodService;

    public void importCsv(MultipartFile file) throws Exception {
        List<Food> foods = new ArrayList<>();
        int lineNumber = 0; // 데이터의 라인 번호를 추적하기 위한 변수

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), "EUC-KR"))) {
            String line;
            br.readLine(); // 첫 번째 라인(헤더)을 읽고 버립니다.
            while ((line = br.readLine()) != null) {
                lineNumber++; // 라인 번호 증가

                String[] fields = line.split(","); // CSV 파일의 구분자에 따라 적절히 조정해야 합니다.
                try {
                    // 필드 값 추출 및 Food 객체 생성
                    Food food = Food.builder()
                            .foodName(fields[1]) // 식품명
                            .makerName(fields[36]) // 제조사명.
                            .calories(parseInt(fields[4], "에너지(kcal)")) // 에너지(kcal).
                            .protein(parseDouble(fields[7], "단백질(g)")) // 단백질(g)
                            .fat(parseDouble(fields[8], "지방(g)")) // 지방(g)
                            .carbohydrate(parseDouble(fields[10], "탄수화물(g)")) // 탄수화물(g)
                            .sugars(parseDouble(fields[11], "당류(g)")) // 당류(g)
                            .sodium(parseInt(fields[17], "나트륨(mg)")) // 나트륨(mg)
                            .cholesterol(parseDouble(fields[26], "콜레스테롤(mg)")) // 콜레스테롤(mg)
                            .saturatedFat(parseDouble(fields[27], "포화지방산(g)")) // 포화지방산(g)
                            .transFat(parseDouble(fields[28], "트랜스지방산(g)")) // 트랜스지방산(g)
                            .servingUnit(extractIntFromString(fields[32])) // 식품중량
                            .build();

                    foods.add(food);
                } catch (Exception e) {
                    // 예외가 발생하면 라인 번호와 예외 정보를 로그로 출력
                    System.err.println("Failed to process line " + lineNumber + ": " + e.getMessage());
                }
            }
        }
        // Batch size를 설정하여 한 번에 저장합니다.
        foodService.saveFoodInBatches(foods, 5000);
    }

    private int parseInt(String value, String fieldName) {
        if (value == null || value.isEmpty()) return 0;
        System.out.println("Value: " + value + ", Field Name: " + fieldName);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " 파싱 중 오류 발생: " + value, e);
        }
    }

    private double parseDouble(String value, String fieldName) {
        if (value == null || value.isEmpty()) return 0.0; // 빈 문자열을 처리
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " 파싱 중 오류 발생: " + value, e);
        }
    }

    // 숫자만 추출하는 메서드
    public static int extractIntFromString(String str) {
        String numberOnly = str.replaceAll("[^0-9]", "");
        if (numberOnly.isEmpty()) {
            return 0; // 숫자가 없는 경우 0을 반환
        }
        return Integer.parseInt(numberOnly);
    }
}
