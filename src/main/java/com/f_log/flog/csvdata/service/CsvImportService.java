package com.f_log.flog.csvdata.service;

import com.f_log.flog.food.domain.Food;
import com.f_log.flog.food.service.FoodService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CsvImportService {

    private static final Logger logger = LoggerFactory.getLogger(CsvImportService.class);

    @Autowired
    private FoodService foodService;

    // 비동기 작업을 위한 Executor (ThreadPool 등)
    @Autowired
    private Executor asyncExecutor;

    public void setAsyncExecutor(Executor asyncExecutor) {
        this.asyncExecutor = asyncExecutor;
    }

    /**
     * CSV 파일을 비동기적으로 import 합니다.
     *
     * @param file 업로드된 CSV 파일
     * @return 작업 완료 여부를 나타내는 CompletableFuture<Void>
     */
    public CompletableFuture<Void> importCsv(MultipartFile file) {
        return CompletableFuture.runAsync(() -> {
                    // CSV 파일을 읽어 Food 객체 리스트로 변환
                    List<Food> foods = parseCsv(file);
                    // 파싱된 Food 리스트를 저장 (재시도 로직 포함)
                    saveFoodListWithRetry(foods, 3);
                }, asyncExecutor)
                .thenAccept(result -> log.info("CSV import completed successfully."))
                .exceptionally(ex -> {
                    log.error("Error during CSV import: {}", ex.getMessage(), ex);
                    throw new RuntimeException(ex);
                });
    }

    /**
     * CSV 파일에서 Food 객체 리스트를 파싱합니다.
     *
     * @param file CSV 파일
     * @return 파싱된 Food 객체 리스트
     */
    private List<Food> parseCsv(MultipartFile file) {
        List<Food> foods = new ArrayList<>();
        int lineNumber = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), "EUC-KR"))) {
            // 헤더 라인 건너뛰기
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] fields = line.split(",");
                try {
                    Food food = Food.builder()
                            .foodName(fields[1])
                            .makerName(fields[36])
                            .calories(parseInt(fields[4], "에너지(kcal)"))
                            .protein(parseDouble(fields[7], "단백질(g)"))
                            .fat(parseDouble(fields[8], "지방(g)"))
                            .carbohydrate(parseDouble(fields[10], "탄수화물(g)"))
                            .sugars(parseDouble(fields[11], "당류(g)"))
                            .sodium(parseInt(fields[17], "나트륨(mg)"))
                            .cholesterol(parseDouble(fields[26], "콜레스테롤(mg)"))
                            .saturatedFat(parseDouble(fields[27], "포화지방산(g)"))
                            .transFat(parseDouble(fields[28], "트랜스지방산(g)"))
                            .servingUnit(extractIntFromString(fields[32]))
                            .build();
                    foods.add(food);
                } catch (Exception e) {
                    log.error("Failed to process line {}: {}", lineNumber, e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("CSV import failed", e);
        }
        return foods;
    }

    /**
     * Food 객체 리스트를 저장합니다.
     * 저장 실패 시 최대 maxAttempts 만큼 재시도합니다.
     *
     * @param foods       저장할 Food 객체 리스트
     * @param maxAttempts 최대 재시도 횟수
     */
    private void saveFoodListWithRetry(List<Food> foods, int maxAttempts) {
        int attempt = 0;
        boolean saved = false;
        while (!saved && attempt < maxAttempts) {
            attempt++;
            try {
                foodService.saveFoodInBatches(foods, 5000);
                saved = true;
            } catch (Exception e) {
                log.error("Attempt {} to save batch failed: {}", attempt, e.getMessage(), e);
                if (attempt < maxAttempts) {
                    try {
                        // 재시도 전에 잠시 대기 (예: 1초)
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    log.error("Saving batch failed after {} attempts.", attempt);
                    throw new RuntimeException("Saving batch failed after " + attempt + " attempts.", e);
                }
            }
        }
    }

    public int parseInt(String value, String fieldName) {
        if (value == null || value.isEmpty()) {
            return 0;
        }
        System.out.println("Value: " + value + ", Field Name: " + fieldName);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " 파싱 중 오류 발생: " + value, e);
        }
    }

    public double parseDouble(String value, String fieldName) {
        if (value == null || value.isEmpty()) {
            return 0.0; // 빈 문자열을 처리
        }
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
