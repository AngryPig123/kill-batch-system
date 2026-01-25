package com.system.batch.killbatchsystem.config.database;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.LocalDateTime;

/**
 * packageName    : com.system.batch.killbatchsystem.config.database
 * fileName       : OlistProductCatTransConfig
 * author         : AngryPig123
 * date           : 26. 1. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 26. 1. 11.        AngryPig123       최초 생성
 */

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OlistProductCatTransConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;

    @Bean
    public Job olistProductCatTransJob() {
        return new JobBuilder("olistProductCatTransJob", jobRepository)
                .start(olistProductCatTransStep())
                .build();
    }

    @Bean
    public Step olistProductCatTransStep() {
        return new StepBuilder("olistProductCatTransStep", jobRepository)
                .<OlistProductCatTrans, OlistProductCatTrans>chunk(5, transactionManager)
                .reader(olistProductCatTransReader())
                .writer(victimWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<OlistProductCatTrans> olistProductCatTransReader() {
        return new JdbcCursorItemReaderBuilder<OlistProductCatTrans>()
                .name("olistProductCatTransReader")
                .dataSource(dataSource)
                .sql("SELECT * FROM OLIST_PRODUCT_CAT_TRANS")
                .beanRowMapper(OlistProductCatTrans.class)
                .build();
    }

    @Bean
    public ItemWriter<OlistProductCatTrans> victimWriter() {
        return items -> {
            for (OlistProductCatTrans item : items) {
                log.info("{}", item);
            }
        };
    }

    @Data
    @NoArgsConstructor
    public static class OlistProductCatTrans {
        private String productCategoryName;
        private String productCategoryNameEnglish;
        private LocalDateTime ingestedAt;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

}
