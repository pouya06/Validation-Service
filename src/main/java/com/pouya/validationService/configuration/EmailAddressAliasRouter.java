package com.pouya.validationService.configuration;

import com.pouya.validationService.controller.EmailAddressAliasValidationController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class EmailAddressAliasRouter {

    @Bean
    public RouterFunction<ServerResponse> emailAddressAliasRoutes
            (EmailAddressAliasValidationController emailAddressAliasValidationController) {
         return RouterFunctions.route(POST("/v1/email")
        .and(accept(MediaType.APPLICATION_JSON)), emailAddressAliasValidationController::getEmailAliasValidation);
    }
}
