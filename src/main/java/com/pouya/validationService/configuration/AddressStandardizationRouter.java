package com.pouya.validationService.configuration;

import com.pouya.validationService.controller.AddressStandardizationController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class AddressStandardizationRouter {

    @Bean
    public RouterFunction<ServerResponse> AddressStandardizationRoutes
            (AddressStandardizationController addressStandardizationController) {
        return RouterFunctions.route(POST("/v1/postaladdress/{countryCode}")
                .and(accept(MediaType.APPLICATION_JSON)), addressStandardizationController::getStandardizedAddress);
    }
}
