package hanglog.member.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleProperties extends ProviderProperties {

    private static final String PROPERTIES_PATH = "${oauth2.provider.google.";

    public GoogleProperties(
            @Value(PROPERTIES_PATH + "client-id}") final String clientId,
            @Value(PROPERTIES_PATH + "client-secret}") final String clientSecret,
            @Value(PROPERTIES_PATH + "redirect-uri}") final String redirectUri,
            @Value(PROPERTIES_PATH + "token-uri}") final String tokenUri,
            @Value(PROPERTIES_PATH + "user-info}") final String userUri) {
        super(clientId, clientSecret, redirectUri, tokenUri, userUri);
    }
}
