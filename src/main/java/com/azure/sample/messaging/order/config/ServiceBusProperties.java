package com.azure.sample.messaging.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("azure.servicebus")
public class ServiceBusProperties {
    /**
     * Service Bus connection string.
     */
    private String connection;

    /**
     * Queue name. Entity path of the queue.
     */
    private String queue;
    private String topicName;
    public String getTopicName() {
        return topicName;
    }


    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }
    public void setQueue(String queue) {
        this.queue = queue;
    }
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    public String getQueue() {
        return queue;
    }

}
