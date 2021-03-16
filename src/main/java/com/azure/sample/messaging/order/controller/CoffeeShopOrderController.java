package com.azure.sample.messaging.order.controller;

import com.azure.core.serializer.json.jackson.JacksonJsonSerializer;
import com.azure.core.util.BinaryData;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderAsyncClient;
import com.azure.sample.messaging.order.mode.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
public class CoffeeShopOrderController {
    @Autowired
    private ServiceBusSenderAsyncClient queueSender;

    @Autowired
    JacksonJsonSerializer jacksonJsonSerializer;

    private int sentMessagesTotal;

    @PostMapping(value = "/sendOrder")
    public String sendOrder(@RequestBody String order) {

        ServiceBusMessage message =  new ServiceBusMessage(order);
        queueSender.sendMessage(message) .subscribe(
                sentSignal ->  System.out.println("Sent Message to Topic"),
                errorSignal ->  System.out.println("Error  signal "+ errorSignal));

        System.out.println((++sentMessagesTotal) + ". Message sent: " + message.getBody().toString());
        return "Total sent messages: " + (sentMessagesTotal) ;
    }

    @PostMapping(value = "/sendOrderCustomSerializer")
    public String sendOrderCustomSerializer(@RequestBody String order) {
        Order myOrder =  new Order();
        myOrder.setOrderDetail(order);
        myOrder.setOrderDateTime(OffsetDateTime.now());

        System.out.println("jacksonJsonSerializer: " + jacksonJsonSerializer);
        BinaryData payload =  BinaryData.fromObject(myOrder,jacksonJsonSerializer );
        ServiceBusMessage message =  new ServiceBusMessage(payload);
        queueSender.sendMessage(message) .subscribe(
                sentSignal ->  System.out.println("Sent Message to Topic"),
                errorSignal ->  System.out.println("Error  signal "+ errorSignal));

        System.out.println((++sentMessagesTotal) + ". Message sent: " + message.getBody().toString());
        return "Total sent messages: " + (sentMessagesTotal) ;
    }
}
