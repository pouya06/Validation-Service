package com.pouya.validationService.controller;

import com.pouya.validationService.dto.PhoneNumberDTO;
import com.pouya.validationService.service.PhoneNumberFormatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PhoneNumberFormatterController {
    private final PhoneNumberFormatterService phoneNumberFormatterService;

    @Autowired
    public PhoneNumberFormatterController(PhoneNumberFormatterService phoneNumberFormatterService) {
        this.phoneNumberFormatterService = phoneNumberFormatterService;
    }

    public Mono<ServerResponse> getFormattedPhoneNumber(ServerRequest request) {
        Mono<PhoneNumberDTO> phoneNumberDTOMono = request.bodyToMono(PhoneNumberDTO.class);

        String countryCode = request.pathVariable("countryCode");

        Mono<PhoneNumberDTO> response = phoneNumberFormatterService.formatPhoneNumber(phoneNumberDTOMono, countryCode);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, PhoneNumberDTO.class);
    }
}
