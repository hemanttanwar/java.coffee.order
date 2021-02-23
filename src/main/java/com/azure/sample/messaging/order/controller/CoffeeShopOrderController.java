package com.azure.sample.messaging.order.controller;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderAsyncClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoffeeShopOrderController {
    @Autowired
    private ServiceBusSenderAsyncClient queueSender;
    private int sentMessages;
    @PostMapping(value = "/sendOrder")
    public String sendOrder(@RequestBody String order) {

        ServiceBusMessage message =  new ServiceBusMessage(order);
        queueSender.sendMessage(message).block();
        System.out.println((sentMessages++) + ". Message sent: " + message.getBody().toString());
        return "Total sent messages: " + (sentMessages) ;
    }
}
