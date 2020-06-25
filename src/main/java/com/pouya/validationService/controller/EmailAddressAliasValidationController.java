package com.pouya.validationService.controller;

import com.pouya.validationService.dto.EmailAddressDTO;
import com.pouya.validationService.service.EmailAddressAliasValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class EmailAddressAliasValidationController {
    private EmailAddressAliasValidationService emailAddressAliasValidationService;

    @Autowired
    public EmailAddressAliasValidationController(EmailAddressAliasValidationService emailAddressAliasValidationService) {
        this.emailAddressAliasValidationService = emailAddressAliasValidationService;
    }

    public Mono<ServerResponse> getEmailAliasValidation(ServerRequest request) {
        Mono<EmailAddressDTO> emailAddressDTOMono = request.bodyToMono(EmailAddressDTO.class);

        Mono<EmailAddressDTO> emailAddressDTOMono1 = emailAddressAliasValidationService.aliasValidation(emailAddressDTOMono);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(emailAddressDTOMono1, EmailAddressDTO.class);
    }
}
