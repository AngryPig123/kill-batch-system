package com.system.batch.killbatchsystem.config.flatFIle;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * packageName    : com.system.batch.killbatchsystem.config
 * fileName       : LogAnalysisJobConfigTest
 * author         : AngryPig123
 * date           : 26. 1. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 26. 1. 11.        AngryPig123       최초 생성
 */

@SpringBootTest
@SpringBatchTest
class LogAnalysisJobConfigTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRegistry jobRegistry;

    @Test
    void 실행() throws Exception {
        Job job = jobRegistry.getJob("logAnalysisJob");
        String path = new ClassPathResource("file/log.txt")
                .getFile()
                .getAbsolutePath();
        JobExecution execution = jobLauncher.run(
                job,
                new JobParametersBuilder()
                        .addString("inputFile", path)
                        .toJobParameters()
        );
        assertThat(execution.getStatus())
                .isEqualTo(BatchStatus.COMPLETED);
    }


}