package com.maro.orderservice.controller;

import com.maro.orderservice.dto.OrderDTO;
import com.maro.orderservice.messagequeue.KafkaProducer;
import com.maro.orderservice.messagequeue.OrderProducer;
import com.maro.orderservice.repository.OrderEntity;
import com.maro.orderservice.service.OrderService;
import com.maro.orderservice.vo.RequestOrder;
import com.maro.orderservice.vo.ResponseOrder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order-service")
@Slf4j
public class OrderController {
    Environment env;
    OrderService orderService;
    KafkaProducer kafkaProducer;
    OrderProducer orderProducer;

    @Autowired
    public OrderController(Environment env, OrderService orderService, KafkaProducer kafkaProducer, OrderProducer orderProducer) {
        this.env = env;
        this.orderService = orderService;
        this.kafkaProducer = kafkaProducer;
        this.orderProducer = orderProducer;
    }

    @GetMapping("/health_check")
    public String status(){
        return String.format("It's working on port %s", env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable String userId, @RequestBody RequestOrder orderDetails){
        log.info("Before added orders data");
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);


        OrderDTO orderDTO = mapper.map(orderDetails, OrderDTO.class);
        orderDTO.setUserId(userId);

        /* JPA */
        /* JPA 사용안하고 카프카로 디비 업데이트 */
//        OrderDTO createdOrder = orderService.createOrder(orderDTO);
//        ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

        /* kafka */
        orderDTO.setOrderId(UUID.randomUUID().toString());
        orderDTO.setTotalPrice(orderDetails.getQty()* orderDetails.getUnitPrice());

        /* send order to kafka */
        kafkaProducer.send("example-catalog-topic", orderDTO);
        orderProducer.send("orders", orderDTO);


        ResponseOrder responseOrder = mapper.map(orderDTO, ResponseOrder.class);

        log.info("After added orders data");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable String userId){
        Iterable<OrderEntity> orderList = orderService.getOrderByUserId(userId);

        log.info("Before retrieve orders data");
        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v->{
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });
        log.info("After retrieve orders data");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }




}
