package hanglog.image.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hanglog.global.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ImageNameUrlConverterTest {

    @DisplayName("URL로 이미지의 이름을 파싱한다.")
    @Test
    void convertUrlToName() {
        // given
        final String url = "https://hanglog.com/img/test.png";
        final String expected = "test.png";

        // when & then
        assertThat(ImageNameUrlConverter.convertUrlToName(url)).isEqualTo(expected);
    }

    @DisplayName("URL의 형식이 잘못된 경우 예외가 발생한다.")
    @ParameterizedTest()
    @ValueSource(strings = {
            "",
            "test.png",
            "invalid/https://hanglog.com/img/test.png",
            "https://hanglog.com/img/https://hanglog.com/img/test.png"
    })
    void convertUrlToName(final String url) {
        // when & then
        assertThatThrownBy(() -> ImageNameUrlConverter.convertUrlToName(url))
                .isInstanceOf(BadRequestException.class)
                .extracting("code")
                .isEqualTo(5005);
    }


    @DisplayName("이미지의 이름으로 URL을 생성한다.")
    @Test
    void convertNameToUrl() {
        // given
        final String name = "test.png";
        final String expected = "https://hanglog.com/img/test.png";

        // when & then
        assertThat(ImageNameUrlConverter.convertNameToUrl(name)).isEqualTo(expected);
    }
}
