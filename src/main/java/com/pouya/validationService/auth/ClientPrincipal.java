package com.pouya.validationService.auth;

import com.pouya.validationService.domain.ClientId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class ClientPrincipal implements Authentication {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1710792753617650875L;
	private final ClientId id;

    public ClientPrincipal(ClientId id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        throw new UnsupportedOperationException("Can't authenticate");
    }

    @Override
    public String getName() {
        return "ClientPrincipal(" + id + ")";
    }

    public ClientId getId() {
        return id;
    }


}
