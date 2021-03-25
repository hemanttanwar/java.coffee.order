package com.azure.sample.messaging.order.controller;

import com.azure.core.serializer.json.jackson.JacksonJsonSerializer;
import com.azure.core.util.BinaryData;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderAsyncClient;
import com.azure.sample.messaging.order.mode.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

@RestController
public class CoffeeShopOrderController {
    Logger logger = LoggerFactory.getLogger(CoffeeShopOrderController.class);

    @Autowired
    private ServiceBusSenderAsyncClient queueSender;

    @Autowired
    JacksonJsonSerializer jacksonJsonSerializer;

    private int sentMessagesTotal;

    @PostMapping(value = "/sendOrder")
    public String sendOrder(@RequestBody String order) {

        ServiceBusMessage message =  new ServiceBusMessage(order);
        var sendOperation = queueSender.sendMessage(message).then(Mono.fromCallable(() -> {
            System.out.println("Sent Message to Topic");
            System.out.println((++sentMessagesTotal) + ". Message sent: " + message.getBody().toString());
            return "Total sent messages: " + (sentMessagesTotal);
        }).onErrorResume(error -> Mono.just("Error signal "+ error)));

        return sendOperation.block();
    }

    /**
     * Uses custom serializer to create json payload from a `Order` object.
     * @param order to send.
     * @return
     */
    @PostMapping(value = "/sendOrderCustomSerializer")
    public String sendOrderCustomSerializer(@RequestBody String order) {
        Order myOrder =  new Order();
        myOrder.setOrderDetail(order);
        myOrder.setOrderDateTime(OffsetDateTime.now());
        BinaryData payload =  BinaryData.fromObject(myOrder, jacksonJsonSerializer );
        ServiceBusMessage message =  new ServiceBusMessage(payload);

        var sendOperation = queueSender.sendMessage(message).then(Mono.fromCallable(() -> {
            System.out.println("Sent Message to Topic");
            System.out.println((++sentMessagesTotal) + ". Message sent: " + message.getBody().toString());
            return "Total sent messages: " + (sentMessagesTotal);
        }).onErrorResume(error -> Mono.just("Error signal "+ error)));

        return sendOperation.block();
    }
}
