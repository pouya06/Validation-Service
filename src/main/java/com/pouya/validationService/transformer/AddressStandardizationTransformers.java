package com.pouya.validationService.transformer;

import com.pouya.validationService.dao.RequestPostalAddress;
import com.pouya.validationService.dao.ResponsePostalAddressINT;
import com.pouya.validationService.dao.ResponsePostalAddressUSA;
import com.pouya.validationService.dto.RequestPostalAddressDTO;
import com.pouya.validationService.dto.ResponseAddressStandardizationDTO;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AddressStandardizationTransformers {
    public RequestPostalAddress tranformFromDTO(RequestPostalAddressDTO requestPostalAddressDTO) {
        return new RequestPostalAddress(
                requestPostalAddressDTO.getGeoStreetAddress1() + " " + requestPostalAddressDTO.getGeoStreetAddress2(),
                requestPostalAddressDTO.getGeoLocality(), requestPostalAddressDTO.getGeoRegion(),
                requestPostalAddressDTO.getGeoPostalCode());
    }

    public Mono<ResponseAddressStandardizationDTO> transformToMonoDTOForUS(Mono<ResponsePostalAddressUSA> standardized, String countryCode) {
        return standardized.map(eachAddress ->
                new ResponseAddressStandardizationDTO(eachAddress.getDeliveryLineOne() + " "
                        + eachAddress.getLastLine() + " " + countryCode.toUpperCase()));
    }

    public Mono<ResponseAddressStandardizationDTO> transformToMonoDTOForINT(Mono<ResponsePostalAddressINT> standardized, String countryCode) {
        return standardized.map(eachAddress ->
                new ResponseAddressStandardizationDTO(eachAddress.getAddress1() + " "
                        + eachAddress.getAddress2() + " " + eachAddress.getAddress3() + " " + countryCode.toUpperCase())
        );
    }
}
