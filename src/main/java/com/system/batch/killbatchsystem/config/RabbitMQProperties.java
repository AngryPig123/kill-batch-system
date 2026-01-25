package com.system.batch.killbatchsystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.system.batch.killbatchsystem.config
 * fileName       : RabbitMQProperties
 * author         : AngryPig123
 * date           : 26. 1. 25.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 26. 1. 25.        AngryPig123       최초 생성
 */
@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitMQProperties {

    private final Queue queue = new Queue();
    private final Exchange exchange = new Exchange();
    private final RoutingKey routingKey = new RoutingKey();

    public Queue getQueue() {
        return queue;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public RoutingKey getRoutingKey() {
        return routingKey;
    }

    public static class Queue {
        /** rabbitmq.queue.job-requests */
        private String jobRequests;

        public String getJobRequests() {
            return jobRequests;
        }

        public void setJobRequests(String jobRequests) {
            this.jobRequests = jobRequests;
        }
    }

    public static class Exchange {
        /** rabbitmq.exchange.job-requests */
        private String jobRequests;

        public String getJobRequests() {
            return jobRequests;
        }

        public void setJobRequests(String jobRequests) {
            this.jobRequests = jobRequests;
        }
    }

    public static class RoutingKey {
        /** rabbitmq.routing-key.job-requests */
        private String jobRequests;

        public String getJobRequests() {
            return jobRequests;
        }

        public void setJobRequests(String jobRequests) {
            this.jobRequests = jobRequests;
        }
    }
}
