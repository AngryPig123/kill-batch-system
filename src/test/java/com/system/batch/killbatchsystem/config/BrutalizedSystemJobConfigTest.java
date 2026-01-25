package com.system.batch.killbatchsystem.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * packageName    : com.system.batch.killbatchsystem.config
 * fileName       : BrutalizedSystemJobConfigTest
 * author         : AngryPig123
 * date           : 26. 1. 18.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 26. 1. 18.        AngryPig123       최초 생성
 */

@Slf4j
@SpringBootTest
@SpringBatchTest
class BrutalizedSystemJobConfigTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRegistry jobRegistry;

    @Test
    void 실행() throws Exception {
        Job job = jobRegistry.getJob("brutalizedSystemJob");
        JobParameters jobParameters = new JobParametersBuilder()
                .addJobParameter("chaos", true, Boolean.class)
                .toJobParameters();

        jobLauncher.run(
                job, jobParameters
        );

        JobInstanceAlreadyCompleteException jobInstanceAlreadyCompleteException = assertThrows(JobInstanceAlreadyCompleteException.class, () -> {
            jobLauncher.run(
                    job, jobParameters
            );
        });

        log.info("exception message = {} ", jobInstanceAlreadyCompleteException.getMessage());

    }

}