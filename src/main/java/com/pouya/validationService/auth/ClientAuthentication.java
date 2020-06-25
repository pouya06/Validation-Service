package com.pouya.validationService.auth;

import com.pouya.validationService.domain.ClientId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ClientAuthentication implements Authentication {

    private final ClientId clientId;
    private final String token;
    private boolean authenticated = false;

    public ClientAuthentication(ClientId clientId, String token) {
        this.clientId = clientId;
        this.token = token;
        this.authenticated = false;
    }

    public String getToken() {
        return token;
    }

    public ClientId getClientId() {
        return clientId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return this;
    }

    @Override
    public Object getDetails() {
        return this;
    }

    @Override
    public Object getPrincipal() {
        return this;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return "Client-" + clientId.getId();
    }

}
