package com.f_log.flog.csvdata.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.f_log.flog.food.service.FoodService;
import java.nio.charset.Charset;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class CsvImportServiceTest {

    @InjectMocks
    private CsvImportService csvImportService;

    @Mock
    private FoodService foodService;

    private MockMultipartFile mockCsvFile;

    @BeforeEach
    void setUp() {
        Executor executor = Executors.newSingleThreadExecutor();
        csvImportService.setAsyncExecutor(executor);

        String csvContent = "foodName,calories,protein,fat,carbohydrate,sugars,sodium,cholesterol,saturatedFat,transFat,servingUnit\n"
                + "Apple,52,0.3,0.2,14,10,1,0,0,0,100\n"
                + "Banana,89,1.1,0.3,23,12,1,0,0,0,118\n";
        mockCsvFile = new MockMultipartFile(
                "file",
                "test.csv",
                "text/csv",
                csvContent.getBytes(Charset.forName("EUC-KR"))
        );
    }

    @Test
    void importCsv_success() throws Exception {
        doNothing().when(foodService).saveFoodInBatches(anyList(), anyInt());

        // CompletableFuture를 반환하므로 join()을 호출하여 작업 완료를 대기합니다.
        assertDoesNotThrow(() -> csvImportService.importCsv(mockCsvFile).join());

        // 비동기 작업이 완료된 후 FoodService가 한 번 호출되었는지 검증합니다.
        verify(foodService, times(1)).saveFoodInBatches(anyList(), eq(5000));
    }

    @Test
    void importCsv_invalidFormat() {
        // 필드 개수가 부족한 CSV 데이터
        String invalidCsvContent = "foodName,calories,protein,fat\n" + "Apple,abc,0.3,0.2\n";
        MockMultipartFile invalidMockFile = new MockMultipartFile(
                "file",
                "invalid.csv",
                "text/csv",
                invalidCsvContent.getBytes(Charset.forName("EUC-KR"))
        );

        assertDoesNotThrow(() -> csvImportService.importCsv(invalidMockFile).join());

    }

    @Test
    void parseInt_validInput() {
        assertEquals(100, csvImportService.parseInt("100", "칼로리"));
    }

    @Test
    void parseInt_invalidInput() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> csvImportService.parseInt("abc", "칼로리"));
        assertTrue(exception.getMessage().contains("파싱 중 오류 발생"));
    }

    @Test
    void parseDouble_validInput() {
        assertEquals(10.5, csvImportService.parseDouble("10.5", "단백질"));
    }

    @Test
    void parseDouble_invalidInput() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> csvImportService.parseDouble("xyz", "단백질"));
        assertTrue(exception.getMessage().contains("파싱 중 오류 발생"));
    }
}
