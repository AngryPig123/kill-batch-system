package com.system.batch.killbatchsystem.config.database;

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
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * packageName    : com.system.batch.killbatchsystem.config.database
 * fileName       : PagingOlistProductCatTransConfig
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
public class PagingOlistProductCatTransConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;

    @Bean
    public Job pagingOlistProductCatTransJob() {
        return new JobBuilder("pagingOlistProductCatTransJob", jobRepository)
                .start(pagingOlistProductCatTransStep())
                .build();
    }

    @Bean
    public Step pagingOlistProductCatTransStep() {
        return new StepBuilder("pagingOlistProductCatTransStep", jobRepository)
                .<OlistProductCatTrans, OlistProductCatTrans>chunk(5, transactionManager)
                .reader(pagingOlistProductCatTransReader())
                .writer(pagingOlistProductCatTransWriter())
                .build();
    }

    @Bean
    public JdbcPagingItemReader<OlistProductCatTrans> pagingOlistProductCatTransReader() {
        return new JdbcPagingItemReaderBuilder<OlistProductCatTrans>()
                .name("pagingOlistProductCatTransReader")
                .dataSource(dataSource)
                .pageSize(10)
                .selectClause("SELECT *")
                .fromClause("FROM OLIST_PRODUCT_CAT_TRANS")
                .sortKeys(Map.of("PRODUCT_CATEGORY_NAME", Order.ASCENDING))
                .beanRowMapper(OlistProductCatTrans.class)
                .build();
    }

    @Bean
    public ItemWriter<OlistProductCatTrans> pagingOlistProductCatTransWriter() {
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
