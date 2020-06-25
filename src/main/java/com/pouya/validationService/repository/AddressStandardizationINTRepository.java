package com.pouya.validationService.repository;

import com.pouya.validationService.dao.RequestPostalAddress;
import com.pouya.validationService.dao.ResponsePostalAddressINT;
import com.pouya.validationService.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AddressStandardizationINTRepository {

    private Logger logger = LoggerFactory.getLogger(AddressStandardizationINTRepository.class);

    private Environment env;
    private final WebClient webClient;

    @Autowired
    public AddressStandardizationINTRepository(Environment env, WebClient.Builder webClientBuilder) {
        this.env = env;
        this.webClient = webClientBuilder.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(env.getProperty("smartystreets.int.url")).build();
    }

    public Mono<ResponsePostalAddressINT> standardized(RequestPostalAddress requestPostalAddress, String countryCode) {
        final String AUTH_ID = this.env.getProperty("smartystreets.auth.id");
        final String AUTH_TOKEN = this.env.getProperty("smartystreets.auth.token");

        Mono<ResponsePostalAddressINT[]> responseINTMono =
                webClient.get()
                        .uri(builder -> builder.scheme("https")
                                .queryParam("auth-id", AUTH_ID)
                                .queryParam("auth-token", AUTH_TOKEN)
                                .queryParam("country", countryCode.toUpperCase())
                                .queryParam("address1", requestPostalAddress.getStreet())
                                .queryParam("locality", requestPostalAddress.getCity())
                                .queryParam("administrative_area", requestPostalAddress.getState())
                                .queryParam("postal_code", requestPostalAddress.getZipCode())
                                .build())
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .flatMap(response -> {
                            if (response.statusCode().is2xxSuccessful()) {
                                return response.bodyToMono(ResponsePostalAddressINT[].class);
                            } else if (response.statusCode().equals(HttpStatus.TOO_MANY_REQUESTS)) {
                                logger.error("Too many request with the same exact input");
                                return Mono.error(new TooManyRequestException("To many request with the same exact input"));
                            } else if (response.statusCode().equals(HttpStatus.BAD_REQUEST)) {
                                logger.error("Bad request from Smarty Street");
                                return Mono.error(new BadRequestException("Bad request from Smarty Street"));
                            } else if (response.statusCode().equals(HttpStatus.PAYMENT_REQUIRED)) {
                                logger.error("Payment Require for subscription");
                                return Mono.error(new PaymentRequirementException("Payment Require for subscription"));
                            }

                            logger.error("Something else went wrong with Smarty Street");
                            return Mono.error(new OtherSmartyStreetException("Something else went wrong with Smarty Street"));
                        });

        return responseINTMono.flatMap(res -> {
            if (res[0].getAnalysisINT().getAnalysis().equalsIgnoreCase("verified")) {
                return Mono.just(res[0]);
            }

            logger.info("Not a valid address {}", requestPostalAddress.getStreet());
            return Mono.error(new NotValidPostalAddressException("Not a valid address"));
        });

    }

}
