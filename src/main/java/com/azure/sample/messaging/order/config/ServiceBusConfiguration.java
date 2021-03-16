package com.azure.sample.messaging.order.config;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusSenderAsyncClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import com.azure.core.serializer.json.jackson.JacksonJsonSerializerBuilder;
import com.azure.core.serializer.json.jackson.JacksonJsonSerializer;


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

    @Bean
    public ObjectMapper objectMapper() {
        org.springframework.http.converter.json.Jackson2ObjectMapperBuilder objectMapperBuilder = new Jackson2ObjectMapperBuilder();

        ObjectMapper objectMapper = objectMapperBuilder.createXmlMapper(false).build();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        //objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    @Bean
    public JacksonJsonSerializer jacksonJsonSerializer() {
        JacksonJsonSerializerBuilder builder = new JacksonJsonSerializerBuilder();
        builder.serializer(objectMapper());
        return builder.build();
    }

}
