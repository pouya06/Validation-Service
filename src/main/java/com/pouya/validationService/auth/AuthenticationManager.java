package com.pouya.validationService.auth;

import com.pouya.validationService.configuration.ApiKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private ApiKeys apiKeys;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        ClientAuthentication clientAuthentication = (ClientAuthentication) authentication;

        if (apiKeys.authenticate(clientAuthentication.getClientId(), clientAuthentication.getToken())) {
            return Mono.just(new ClientPrincipal(clientAuthentication.getClientId()));
        }

        return Mono.empty();
    }
}
