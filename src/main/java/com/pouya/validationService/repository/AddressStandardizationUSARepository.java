package com.pouya.validationService.repository;

import com.pouya.validationService.dao.RequestPostalAddress;
import com.pouya.validationService.dao.ResponsePostalAddressUSA;
import com.pouya.validationService.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Component
public class AddressStandardizationUSARepository {

    private Logger logger = LoggerFactory.getLogger(AddressStandardizationUSARepository.class);

    private Environment env;
    private final WebClient webClient;


    @Autowired
    public AddressStandardizationUSARepository(Environment env, WebClient.Builder webClientBuilder) {
        this.env = env;
        this.webClient = webClientBuilder.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(env.getProperty("smartystreets.us.url")).build();
    }

    public Mono<ResponsePostalAddressUSA> standardized(RequestPostalAddress requestPostalAddress) {
        final String AUTH_ID = this.env.getProperty("smartystreets.auth.id");
        final String AUTH_TOKEN = this.env.getProperty("smartystreets.auth.token");
        final String DPV_FOOTNOTES = this.env.getProperty("smartystreets.dpv.footnotes");

        Mono<ResponsePostalAddressUSA[]> responseUSAMono =
                webClient.post()
                        .uri(builder -> builder.scheme("https")
                                .queryParam("auth-id", AUTH_ID)
                                .queryParam("auth-token", AUTH_TOKEN)
                                .build())
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromObject(Arrays.asList(requestPostalAddress)))
                        .exchange()
                        .flatMap(response -> {
                            if (response.statusCode().is2xxSuccessful()) {
                                return response.bodyToMono(ResponsePostalAddressUSA[].class);
                            } else if (response.statusCode().equals(HttpStatus.TOO_MANY_REQUESTS)) {
                                logger.error("Too many request with the same exact input");
                                Mono.error(new TooManyRequestException("To many request with the same exact input"));
                            } else if (response.statusCode().equals(HttpStatus.BAD_REQUEST)) {
                                logger.error("Bad request from Smarty Street");
                                Mono.error(new BadRequestException("Bad request from Smarty Street"));
                            } else if (response.statusCode().equals(HttpStatus.PAYMENT_REQUIRED)) {
                                logger.error("Payment Require for subscription");
                                Mono.error(new PaymentRequirementException("Payment Require for subscription"));
                            }

                            logger.error("Something else went wrong with Smarty Street");
                            return Mono.error(new OtherSmartyStreetException("Something else went wrong with Smarty Street"));
                        });

        return responseUSAMono.flatMap(res -> {
            if (res.length == 0) {
                logger.info("Not a valid address {}", requestPostalAddress.getStreet());
                return  Mono.error(new NotValidPostalAddressException("Not a valid address"));
            }

            if (res[0].getAnalysis().getActive().equalsIgnoreCase("Y")
                    && res[0].getAnalysis().getDpvFootnotes().equalsIgnoreCase(DPV_FOOTNOTES)) {
                return Mono.just(res[0]);
            }

            logger.info("Not a valid address {}", requestPostalAddress.getStreet());
            return  Mono.error(new NotValidPostalAddressException("Not a valid address"));
        });

    }

}
