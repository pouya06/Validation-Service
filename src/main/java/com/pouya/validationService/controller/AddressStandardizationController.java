package com.pouya.validationService.controller;

import com.pouya.validationService.dto.RequestPostalAddressDTO;
import com.pouya.validationService.dto.ResponseAddressStandardizationDTO;
import com.pouya.validationService.exception.NotAValidEndpointExeption;
import com.pouya.validationService.service.AddressStandardizationService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AddressStandardizationController {
    private final AddressStandardizationService addressStandardizationService;

    public AddressStandardizationController(AddressStandardizationService addressStandardizationService) {
        this.addressStandardizationService = addressStandardizationService;
    }

    public Mono<ServerResponse> getStandardizedAddress(ServerRequest request) {
        Mono<RequestPostalAddressDTO> requestPostalAddressDTOMono = request.bodyToMono(RequestPostalAddressDTO.class);

        String countryCode = request.pathVariable("countryCode");

        if (countryCode.length() != 2) {
            Mono.just(new NotAValidEndpointExeption("Country code must be two letters"));
        }

        Mono<ResponseAddressStandardizationDTO> response = addressStandardizationService.standardized(requestPostalAddressDTOMono, countryCode);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, ResponseAddressStandardizationDTO.class);
    }
}
