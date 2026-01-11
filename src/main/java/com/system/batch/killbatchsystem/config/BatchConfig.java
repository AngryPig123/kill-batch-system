package com.system.batch.killbatchsystem.config;

import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * packageName    : com.system.batch.killbatchsystem.config
 * fileName       : BatchConfig
 * author         : AngryPig123
 * date           : 26. 1. 10.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 26. 1. 10.        AngryPig123       최초 생성
 */
@Configuration
public class BatchConfig extends DefaultBatchConfiguration {
    //  배치와 관련된 메타 데이터를 배치 코어 라이브러리 안에 포함되어있는 ddl 문으로 구성해주는 부분
    @Bean
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("org/springframework/batch/core/schema-h2.sql")
                .build();
    }
    //  트랜잭션 플랫폼 설정
    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }
}