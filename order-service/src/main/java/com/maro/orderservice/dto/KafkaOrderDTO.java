package com.maro.orderservice.dto;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class KafkaOrderDTO implements Serializable {
    private Schema schema;
    private Payload payload;
}
