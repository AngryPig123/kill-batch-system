package com.system.batch.killbatchsystem.config;

import org.springframework.boot.autoconfigure.batch.BatchTaskExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

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
public class BatchConfig {

    //  웹 요청에 대응 하기 위한 비동기 실행을 위해
    @Bean
    @BatchTaskExecutor
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

}