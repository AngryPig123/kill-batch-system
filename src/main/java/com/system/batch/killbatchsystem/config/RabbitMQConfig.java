package com.system.batch.killbatchsystem.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.system.batch.killbatchsystem.config
 * fileName       : RabbitMQConfig
 * author         : AngryPig123
 * date           : 26. 1. 25.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 26. 1. 25.        AngryPig123       최초 생성
 */
@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final RabbitMQProperties properties;

    @Bean
    public Queue jobRequestsQueue() {
        return QueueBuilder.durable(properties.getQueue().getJobRequests()).build();
    }

    @Bean
    public DirectExchange jobRequestsExchange() {
        return new DirectExchange(properties.getExchange().getJobRequests());
    }

    @Bean
    public Binding jobRequestsBinding() {
        return BindingBuilder
                .bind(jobRequestsQueue())
                .to(jobRequestsExchange())
                .with(properties.getRoutingKey().getJobRequests());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }
}
