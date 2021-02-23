package com.azure.sample.messaging.order.config;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusSenderAsyncClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(ServiceBusProperties.class)
public class ServiceBusConfiguration {
    private ServiceBusProperties properties;

    public ServiceBusConfiguration(ServiceBusProperties properties) {
        this.properties = properties;
    }
    @Bean
    public ServiceBusSenderAsyncClient queueSender() {

        return new ServiceBusClientBuilder()
                .connectionString(properties.getConnection())
                .sender()
                .topicName(properties.getTopicName())
               // .queueName(properties.getQueue())
                .buildAsyncClient();
    }

}
