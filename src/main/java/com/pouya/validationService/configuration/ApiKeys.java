package com.pouya.validationService.configuration;


import com.pouya.validationService.domain.ClientId;
import com.google.common.collect.ImmutableMultimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ApiKeys {

    private final ImmutableMultimap<ClientId, String> apiKeys;

    @Autowired
    public ApiKeys(ApiKeysConfiguration configuration) {
        apiKeys = configuration.build();
    }

    public boolean authenticate(ClientId clientId, String apiKey) {
        return apiKeys.get(clientId).contains(apiKey);
    }

}


