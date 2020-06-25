package com.pouya.validationService.service;

import com.pouya.validationService.dto.EmailAddressDTO;
import com.pouya.validationService.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class EmailAddressAliasValidationService {
    private Util util;

    @Autowired
    public EmailAddressAliasValidationService(Util util) {
        this.util = util;
    }

    public Mono<EmailAddressDTO> aliasValidation(Mono<EmailAddressDTO> emailAddressDTOMono) {
        Mono<String> utilMonoReturn = emailAddressDTOMono.map(email ->
                util.handleGmailAndHotmail(email.getEmailAddress())
        );

        return utilMonoReturn.map(res -> {
            EmailAddressDTO emailAddressDTO = new EmailAddressDTO();
            emailAddressDTO.setEmailAddress(res);
            return emailAddressDTO;
        });

    }
}
