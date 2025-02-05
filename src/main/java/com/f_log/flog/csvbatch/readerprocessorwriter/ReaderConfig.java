package com.f_log.flog.csvbatch.readerprocessorwriter;

import com.f_log.flog.csvbatch.dto.FoodRawDTO;
import com.f_log.flog.csvbatch.dto.FoodRawDTOFieldSetMapper;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ReaderConfig {


    @Bean
    public FlatFileItemReader<FoodRawDTO> foodItemReader() {
        FlatFileItemReader<FoodRawDTO> reader = new FlatFileItemReader<>();
        reader.setResource(
                new FileSystemResource("src/main/java/com/f_log/flog/csvdata/nutritiondata.csv"));
        reader.setEncoding("EUC-KR");
        reader.setLinesToSkip(1);

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setQuoteCharacter('"');
        tokenizer.setNames(new String[]{
                "dummy",        // 0: 식품코드
                "foodName",     // 1: 식품명
                "dummy",        // 2: 데이터구분코드
                "dummy",        // 3: 데이터구분명
                "calories",     // 4: 에너지(kcal)
                "dummy",        // 5: 영양성분함량기준량
                "dummy",        // 6: 수분(g)
                "protein",      // 7: 단백질(g)
                "fat",          // 8: 지방(g)
                "dummy",        // 9: 회분(g)
                "carbohydrate", // 10: 탄수화물(g)
                "sugars",       // 11: 당류(g)
                "dummy",        // 12: 식이섬유(g)
                "dummy",        // 13: 칼슘(mg)
                "dummy",        // 14: 철(mg)
                "dummy",        // 15: 인(mg)
                "dummy",        // 16: 칼륨(mg)
                "sodium",       // 17: 나트륨(mg)
                "dummy",        // 18: 비타민 A(μg RAE)
                "dummy",        // 19: 레티놀(μg)
                "dummy",        // 20: 베타카로틴(μg)
                "dummy",        // 21: 티아민(mg)
                "dummy",        // 22: 리보플라빈(mg)
                "dummy",        // 23: 니아신(mg)
                "dummy",        // 24: 비타민 C(mg)
                "dummy",        // 25: 비타민 D(μg)
                "cholesterol",  // 26: 콜레스테롤(mg)
                "saturatedFat", // 27: 포화지방산(g)
                "transFat",     // 28: 트랜스지방산(g)
                "dummy",        // 29: 폐기율(%)
                "dummy",        // 30: 출처코드
                "dummy",        // 31: 출처명
                "servingUnit",  // 32: 식품중량
                "dummy",        // 33: 수입여부
                "dummy",        // 34: 원산지국코드
                "dummy",        // 35: 원산지국명
                "makerName",    // 36: 업체명
                "dummy",        // 37: 데이터생성방법코드
                "dummy",        // 38: 데이터생성방법명
                "dummy",        // 39: 데이터생성일자
                "dummy",        // 40: 데이터기준일자
                "dummy",        // 41: 제공기관코드
                "dummy"         // 42: 제공기관명
        });

        DefaultLineMapper<FoodRawDTO> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        // 여기서 기존 BeanWrapperFieldSetMapper 대신 커스텀 FieldSetMapper 사용
        lineMapper.setFieldSetMapper(new FoodRawDTOFieldSetMapper());
        reader.setLineMapper(lineMapper);
        return reader;
    }
}

