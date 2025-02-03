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
     * CSV 파일을 비동기적으로 import합니다.
     * - CompletableFuture.runAsync()로 작업을 비동기 실행하고,
     * - 작업 완료 시 thenAccept()를 통해 성공 메시지를 출력하며,
     * - 예외 발생 시 exceptionally()에서 오류를 포착하고 재던져서 전체 체인이 예외로 완료되도록 합니다.
     *
     * @param file 업로드된 CSV 파일
     * @return 작업 완료 여부를 나타내는 CompletableFuture<Void>
     */
    public CompletableFuture<Void> importCsv(MultipartFile file) {
        return CompletableFuture.runAsync(() -> {
                    List<Food> foods = new ArrayList<>();
                    int lineNumber = 0;

                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(file.getInputStream(), "EUC-KR"))) {
                        String line;
                        br.readLine(); // 첫 번째 라인(헤더)을 무시
                        while ((line = br.readLine()) != null) {
                            lineNumber++;
                            String[] fields = line.split(",");
                            try {
                                // 각 필드 값 추출 후 Food 객체 생성
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
                                // 각 라인 처리 중 오류가 발생하면 로그를 남기고 해당 라인은 건너뜁니다.
                                logger.error("Failed to process line {}: {}", lineNumber, e.getMessage(), e);

//                                throw new RuntimeException("CSV 데이터 처리 중 오류 발생 (줄 번호: " + lineNumber + ")", e);
                            }
                        }
                    } catch (Exception e) {
                        // CSV 파일 전체 읽기 중 오류 발생 시 RuntimeException을 발생시켜 CompletableFuture 체인에서 포착하게 함
                        throw new RuntimeException("CSV import failed", e);
                    }

                    // foodService.saveFoodInBatches(foods, 5000); 을 호출할 때 재시도 로직 적용
                    int maxAttempts = 3;
                    int attempt = 0;
                    boolean saved = false;
                    while (!saved && attempt < maxAttempts) {
                        attempt++;
                        try {
                            foodService.saveFoodInBatches(foods, 5000);
                            saved = true;
                        } catch (Exception e) {
                            logger.error("Attempt {} to save batch failed: {}", attempt, e.getMessage(), e);
                            if (attempt < maxAttempts) {
                                try {
                                    // 재시도 전에 잠시 대기 (예: 1초)
                                    Thread.sleep(1000);
                                } catch (InterruptedException ie) {
                                    Thread.currentThread().interrupt();
                                }
                            } else {
                                logger.error("Saving batch failed after {} attempts. Skipping this batch.", attempt);
                            }
                        }
                    }
                }, asyncExecutor)
                // 작업이 정상적으로 완료되면 후속 작업(여기서는 성공 메시지 출력)을 실행
                .thenAccept(result -> logger.info("CSV import completed successfully."))
                // 작업 중 예외가 발생하면 여기서 포착하여 로그 출력 및 필요 시 대체 로직 실행
                .exceptionally(ex -> {
                    logger.error("Error during CSV import: {}", ex.getMessage(), ex);
                    // 예외 상황에 대한 추가 처리(재시도 로직 등)를 구현할 수 있습니다.
                    throw new RuntimeException(ex);
                });
    }

//    public void importCsv(MultipartFile file) throws Exception {
//        List<Food> foods = new ArrayList<>();
//        int lineNumber = 0; // 데이터의 라인 번호를 추적하기 위한 변수
//
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), "EUC-KR"))) {
//            String line;
//            br.readLine(); // 첫 번째 라인(헤더)을 읽고 버립니다.
//            while ((line = br.readLine()) != null) {
//                lineNumber++; // 라인 번호 증가
//
//                String[] fields = line.split(","); // CSV 파일의 구분자에 따라 적절히 조정해야 합니다.
//                try {
//                    // 필드 값 추출 및 Food 객체 생성
//                    Food food = Food.builder()
//                            .foodName(fields[1]) // 식품명
//                            .makerName(fields[36]) // 제조사명.
//                            .calories(parseInt(fields[4], "에너지(kcal)")) // 에너지(kcal).
//                            .protein(parseDouble(fields[7], "단백질(g)")) // 단백질(g)
//                            .fat(parseDouble(fields[8], "지방(g)")) // 지방(g)
//                            .carbohydrate(parseDouble(fields[10], "탄수화물(g)")) // 탄수화물(g)
//                            .sugars(parseDouble(fields[11], "당류(g)")) // 당류(g)
//                            .sodium(parseInt(fields[17], "나트륨(mg)")) // 나트륨(mg)
//                            .cholesterol(parseDouble(fields[26], "콜레스테롤(mg)")) // 콜레스테롤(mg)
//                            .saturatedFat(parseDouble(fields[27], "포화지방산(g)")) // 포화지방산(g)
//                            .transFat(parseDouble(fields[28], "트랜스지방산(g)")) // 트랜스지방산(g)
//                            .servingUnit(extractIntFromString(fields[32])) // 식품중량
//                            .build();
//
//                    foods.add(food);
//                } catch (Exception e) {
//                    // 예외가 발생하면 라인 번호와 예외 정보를 로그로 출력
//                    System.err.println("Failed to process line " + lineNumber + ": " + e.getMessage());
//                    throw new RuntimeException("CSV 데이터 처리 중 오류 발생 (줄 번호: " + lineNumber + ")", e);
//                }
//            }
//        }
//        // Batch size를 설정하여 한 번에 저장합니다.
//        foodService.saveFoodInBatches(foods, 5000);
//    }

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
