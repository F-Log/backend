package com.f_log.flog.csvbatch.config;


import com.f_log.flog.csvbatch.dto.FoodRawDTO;
import com.f_log.flog.food.domain.Food;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchJobConfig {

    private final String JOB_NAME = "csvImportJob";
    private final String STEP_NAME = "csvImportStep";

    // 기존에 선언한 reader, processor, writer 빈이 주입됩니다.
    private final ItemReader<FoodRawDTO> foodItemReader;
    private final ItemProcessor<FoodRawDTO, Food> foodItemProcessor;
    private final ItemWriter<Food> foodItemWriter;

    @Bean
    public Job csvImportJob(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(csvImportStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    @JobScope
    public Step csvImportStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<FoodRawDTO, Food>chunk(1000, transactionManager)
                .reader(foodItemReader)
                .processor(foodItemProcessor)
                .writer(foodItemWriter)
                .build();
    }
}
