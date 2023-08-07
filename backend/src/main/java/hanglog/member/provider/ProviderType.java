package hanglog.member.provider;


import static hanglog.global.exception.ExceptionCode.NOT_SUPPORTED_OAUTH_SERVICE;

import hanglog.global.exception.AuthException;
import hanglog.member.domain.auth.google.GoogleUserInfo;
import hanglog.member.domain.auth.kakao.KakaoUserInfo;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
public enum ProviderType {

    GOOGLE("google", GoogleUserInfo.class),
    KAKAO("kakao", KakaoUserInfo.class);

    private final String provider;
    private final Class<?> responseDto;
    private ProviderProperties properties;

    ProviderType(final String provider, final Class<?> responseDto) {
        this.provider = provider;
        this.responseDto = responseDto;
    }

    public static ProviderType mappingProvider(final String provider) {
        return Arrays.stream(values())
                .filter(value -> value.provider.equals(provider))
                .findAny()
                .orElseThrow(() -> new AuthException(NOT_SUPPORTED_OAUTH_SERVICE));
    }

    @Component
    private static class PropertiesConfig {

        private final ProviderProperties googleProperties;
        private final ProviderProperties kakaoProperties;

        public PropertiesConfig(
                final ProviderProperties googleProperties,
                final ProviderProperties kakaoProperties
        ) {
            this.googleProperties = googleProperties;
            this.kakaoProperties = kakaoProperties;
        }

        @PostConstruct
        public void postConstruct() {
            GOOGLE.properties = googleProperties;
            KAKAO.properties = kakaoProperties;
        }
    }
}
