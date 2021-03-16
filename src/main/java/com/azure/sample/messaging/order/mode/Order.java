package com.azure.sample.messaging.order.mode;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.time.OffsetDateTime;

public final class Order {
    @JsonProperty
    String orderDetail;

    @JsonProperty
    private OffsetDateTime orderDateTime;

    @JsonGetter
    public String getOrderDetail () {
        return orderDetail;
    }

    @JsonSetter
    public void setOrderDetail(String order) {
        this.orderDetail = order;
    }

    @JsonGetter
    public OffsetDateTime getOrderDateTime () {
        return orderDateTime;
    }

    @JsonSetter
    public void setOrderDateTime(OffsetDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }
}
