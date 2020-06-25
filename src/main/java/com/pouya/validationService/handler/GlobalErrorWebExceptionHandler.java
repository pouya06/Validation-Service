package com.pouya.validationService.handler;

import com.pouya.validationService.exception.*;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        if (ex instanceof MethodArgumentNotValidException) {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            byte[] bytes = ex.getMessage().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }

        if (ex instanceof NotAValidEndpointExeption) {
            exchange.getResponse().setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
            byte[] bytes = ex.getMessage().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }

        if (ex instanceof NotValidPostalAddressException) {
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
            byte[] bytes = ex.getMessage().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }

        if (ex instanceof TooManyRequestException) {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            byte[] bytes = ex.getMessage().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }

        if (ex instanceof BadRequestException) {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            byte[] bytes = ex.getMessage().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }

        if (ex instanceof PaymentRequirementException) {
            exchange.getResponse().setStatusCode(HttpStatus.PAYMENT_REQUIRED);
            byte[] bytes = ex.getMessage().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }

        if (ex instanceof InvalidPhoneNumberException) {
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            byte[] bytes = ex.getMessage().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }

        if (ex instanceof OtherSmartyStreetException) {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            byte[] bytes = ex.getMessage().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }

        return Mono.error(ex);
    }
}