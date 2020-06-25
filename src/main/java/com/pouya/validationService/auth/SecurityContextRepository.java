package com.pouya.validationService.auth;

import com.pouya.validationService.domain.ClientId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
        throw new IllegalArgumentException("do not touch me.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
        String token = getTokenFromHeader(serverWebExchange);
        if(token.isEmpty()) {
            return Mono.empty();
        }

        String[] splittedToken = token.split("\\.");
        if (splittedToken.length != 2) {
            return Mono.empty();
        }

        try {

            ClientId clientId = new ClientId(Integer.valueOf(splittedToken[0]));
            ClientAuthentication clientAuthentication = new ClientAuthentication(clientId, splittedToken[1]);
            return this.authenticationManager.authenticate(clientAuthentication).map((authentication) ->
                    new SecurityContextImpl(authentication));

        } catch (Exception e) {
            return Mono.error(e); // add a customized error back
        }
    }

    private String getTokenFromHeader(ServerWebExchange serverWebExchange) {
        String authHeader = serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return "";
        }

        return authHeader.substring(7).trim();
    }
}
