package hanglog.member.domain.auth;

import static hanglog.global.exception.ExceptionCode.NOT_SUPPORTED_OAUTH_SERVICE;

import hanglog.global.exception.AuthException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Providers {

    private final List<Provider> providers;

    public Providers(final List<Provider> providers) {
        this.providers = providers;
    }

    public Provider getProvider(final String providerName) {
        return providers.stream()
                .filter(provider -> provider.is(providerName))
                .findFirst()
                .orElseThrow(() -> new AuthException(NOT_SUPPORTED_OAUTH_SERVICE));
    }
}
