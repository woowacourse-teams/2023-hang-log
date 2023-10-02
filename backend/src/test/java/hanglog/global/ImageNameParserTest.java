package hanglog.global;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class ImageNameParserTest {

    @DisplayName("URL로 이미지의 이름을 파싱한다.")
    @Test
    void parse() {
        // given
        final String url = "https://hanglog.com/img/test.png";
        final String expected = "test.png";

        // when & then
        assertThat(ImageNameParser.parse(url)).isEqualTo(expected);
    }
}
