package com.system.batch.killbatchsystem.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.batch.integration.launch.JobLaunchingGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;

/**
 * packageName    : com.system.batch.killbatchsystem.config
 * fileName       : JobLaunchIntegrationConfig
 * author         : AngryPig123
 * date           : 26. 1. 25.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 26. 1. 25.        AngryPig123       최초 생성
 */
@Slf4j
@Configuration
@EnableIntegration
@RequiredArgsConstructor
public class JobLaunchIntegrationConfig {
    private final JobRegistry jobRegistry;
    private final JobLauncher jobLauncher;
    private final ObjectMapper objectMapper;
    private final RabbitMQProperties rabbitMQProperties;

    @Bean
    public JobLaunchingGateway jobLaunchingGateway() {
        return new JobLaunchingGateway(jobLauncher);
    }

    @Bean
    public IntegrationFlow jobLaunchFlow(ConnectionFactory connectionFactory) {
        return IntegrationFlow
                .from(Amqp.inboundAdapter(connectionFactory, rabbitMQProperties.getQueue().getJobRequests())
                        .messageConverter(new Jackson2JsonMessageConverter()))
                .channel(rabbitJobRequestChannel())
                .transform(this::createJobLaunchRequest)
                .handle(jobLaunchingGateway())
                .handle(message -> {
                    log.info("💀 [KILL-9]: Job execution completed: {}", message.getPayload());
                })
                .get();
    }

    @Bean
    public MessageChannel rabbitJobRequestChannel() {
        return new QueueChannel(10);
    }

    private JobLaunchRequest createJobLaunchRequest(String jsonMessage) {
        try {
            JsonNode node = objectMapper.readTree(jsonMessage);

            String jobName = node.get("jobName").asText();
            long timestamp = node.get("timestamp").asLong();

            Job job = jobRegistry.getJob(jobName);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", timestamp)
                    .toJobParameters();

            log.info("💀 [KILL-9]: Created JobLaunchRequest for job: {}", jobName);

            return new JobLaunchRequest(job, jobParameters);
        } catch (Exception e) {
            log.error("💀 [KILL-9]: Failed to create JobLaunchRequest: ", e);
            throw new RuntimeException("Failed to process message: " + jsonMessage, e);
        }
    }
}
