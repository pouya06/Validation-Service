package com.pouya.validationService.service;

import com.pouya.validationService.dao.RequestPostalAddress;
import com.pouya.validationService.dao.ResponsePostalAddressINT;
import com.pouya.validationService.dao.ResponsePostalAddressUSA;
import com.pouya.validationService.dto.RequestPostalAddressDTO;
import com.pouya.validationService.dto.ResponseAddressStandardizationDTO;
import com.pouya.validationService.repository.AddressStandardizationINTRepository;
import com.pouya.validationService.repository.AddressStandardizationUSARepository;
import com.pouya.validationService.transformer.AddressStandardizationTransformers;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AddressStandardizationService {
    private AddressStandardizationUSARepository addressStandardizationUSARepository;
    private AddressStandardizationINTRepository addressStandardizationINTRepository;
    private AddressStandardizationTransformers addressStandardizationTransformers;

    public AddressStandardizationService(AddressStandardizationUSARepository addressStandardizationUSARepository,
                                         AddressStandardizationINTRepository addressStandardizationINTRepository,
                                         AddressStandardizationTransformers addressStandardizationTransformers) {
        this.addressStandardizationUSARepository = addressStandardizationUSARepository;
        this.addressStandardizationINTRepository = addressStandardizationINTRepository;
        this.addressStandardizationTransformers = addressStandardizationTransformers;
    }

    public Mono<ResponseAddressStandardizationDTO> standardized(Mono<RequestPostalAddressDTO> requestPostalAddressDTOMono,
                                                                String countryCode) {
        return requestPostalAddressDTOMono.flatMap(address -> {
            RequestPostalAddress requestPostalAddress = addressStandardizationTransformers.tranformFromDTO(address);

            if (countryCode.equalsIgnoreCase("us")) {
                Mono<ResponsePostalAddressUSA> standardizedFromUSA = addressStandardizationUSARepository.standardized(requestPostalAddress);
                return addressStandardizationTransformers.transformToMonoDTOForUS(standardizedFromUSA, countryCode);
            }

            Mono<ResponsePostalAddressINT> standardizedFromINT = addressStandardizationINTRepository.standardized(requestPostalAddress, countryCode);
            return addressStandardizationTransformers.transformToMonoDTOForINT(standardizedFromINT, countryCode);
        });

    }
}
