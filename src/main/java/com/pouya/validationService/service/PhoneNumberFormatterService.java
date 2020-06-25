package com.pouya.validationService.service;

import com.pouya.validationService.dto.PhoneNumberDTO;
import com.pouya.validationService.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PhoneNumberFormatterService {
    private Util util;

    @Autowired
    public PhoneNumberFormatterService(Util util) {
        this.util = util;
    }

    public Mono<PhoneNumberDTO> formatPhoneNumber(Mono<PhoneNumberDTO> phoneNumberDTOMono, String countryCode) {
        if (countryCode.equalsIgnoreCase("us") || countryCode.equalsIgnoreCase("ca")) {
            return phoneNumberDTOMono.map(phone ->
                    util.handlePhoneNumberForUSOrCanada(phone.getPhoneNumber(), countryCode)
            ).map(res -> phoneNumberDTOBuilder(res))
                    .onErrorResume(e -> Mono.error(e));
        }

        return phoneNumberDTOMono.map(phone ->
                util.handlePhoneNumberForInternational(phone.getPhoneNumber())
        ).map(res -> phoneNumberDTOBuilder(res));

    }

    private PhoneNumberDTO phoneNumberDTOBuilder(String res) {
        PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO();
        phoneNumberDTO.setPhoneNumber(res);
        return phoneNumberDTO;
    }
}
