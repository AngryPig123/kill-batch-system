package com.system.batch.killbatchsystem.config.flatFIle;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * packageName    : com.system.batch.killbatchsystem.config
 * fileName       : SystemFailureJobConfigTest
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
class SystemFailureJobConfigTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRegistry jobRegistry;

    @Test
    void 실행() throws Exception {
        Job job = jobRegistry.getJob("systemFailureJob");
        String path = new ClassPathResource("file/system-failures.csv")
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