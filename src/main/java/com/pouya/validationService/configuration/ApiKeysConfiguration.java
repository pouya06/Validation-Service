package com.pouya.validationService.configuration;

import com.pouya.validationService.domain.ClientId;
import com.google.common.collect.ImmutableMultimap;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties()
class ApiKeysConfiguration {

    private final Map<Integer, String> apiKeys = new HashMap<>();

    public ImmutableMultimap<ClientId, String> build() {

        ImmutableMultimap.Builder<ClientId, String> builder = ImmutableMultimap.builder();

        apiKeys.forEach((clientIdAsInt, apiKeysAsString) ->

                Arrays.asList(apiKeysAsString.split(",")).stream().forEach(string ->
                        builder.put(new ClientId(clientIdAsInt), string.trim()))

        );

        return builder.build();
    }

    public Map<Integer, String> getApiKeys() {
        return apiKeys;
    }

}
