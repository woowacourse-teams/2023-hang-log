package hanglog.member.provider;


import hanglog.member.service.dto.GoogleUserInfoResponseDto;
import hanglog.member.service.dto.KakaoUserInfoResponseDto;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
public enum Provider {

    GOOGLE("google", GoogleUserInfoResponseDto.class),
    KAKAO("kakao", KakaoUserInfoResponseDto.class);
    private final String provider;
    private final Class<?> responseDto;
    private ProviderProperties properties;

    Provider(final String provider, final Class<?> responseDto) {
        this.provider = provider;
        this.responseDto = responseDto;
    }

    public static Provider mappingProvider(final String provider) {
        return Arrays.stream(values()).filter(value -> value.provider.equals(provider))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 OAuth 서비스는 제공하지 않습니다."));
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
