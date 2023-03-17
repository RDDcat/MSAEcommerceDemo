package com.maro.gateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
    public LoggingFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // pre filter
//        return ((exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//            log.info("GlobalFilter base message: {}", config.getBaseMessage());
//
//            if(config.isPostLogger()){
//                log.info("GlobalFilter Start: request id -> {}", request.getId());
//            }
//
//            // post filter
//            return chain.filter(exchange).then(Mono.fromRunnable(()-> {
//                if(config.isPostLogger()){
//                    log.info("GlobalFilter End: response code -> {}", response.getStatusCode());
//                }
//            }));
//        });
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            log.info("Logging base message: {}", config.getBaseMessage());

            if(config.isPostLogger()){
                log.info("Logging Start: request id -> {}", request.getId());
            }

            // post filter
            return chain.filter(exchange).then(Mono.fromRunnable(()-> {
                if(config.isPostLogger()){
                    log.info("Logging End: response code -> {}", response.getStatusCode());
                }
            }));
        }, Ordered.HIGHEST_PRECEDENCE);

        return filter;
    }

    @Data
    public static class Config{
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
