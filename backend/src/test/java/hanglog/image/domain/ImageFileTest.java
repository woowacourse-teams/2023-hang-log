package hanglog.image.domain;

import static java.io.InputStream.nullInputStream;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hanglog.global.exception.ImageException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class ImageFileTest {

    @DisplayName("이미지 파일이 Null아면 예외가 발생한다.")
    @Test
    void validateNullImage() throws IOException {
        // given
        final MockMultipartFile nullFile = new MockMultipartFile(
                "images",
                null,
                null,
                nullInputStream()
        );

        // when & then
        assertThatThrownBy(() -> new ImageFile(nullFile))
                .isInstanceOf(ImageException.class)
                .extracting("code")
                .isEqualTo(5002);
    }
}
