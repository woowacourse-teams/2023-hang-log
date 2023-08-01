package hanglog.image.service;

import static java.io.InputStream.nullInputStream;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hanglog.global.exception.ImageException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @InjectMocks
    private ImageService imageService;

    @DisplayName("입력된 이미지의 수가 0개이면 예외가 발생한다.")
    @Test
    void save_EmptyException() {
        // given
        final List<MultipartFile> files = List.of();

        // when & then
        assertThatThrownBy(() -> imageService.save(files))
                .isInstanceOf(ImageException.class)
                .extracting("code")
                .isEqualTo(5003);
    }

    @DisplayName("입력된 이미지의 수가 10개를 초과하면 예외가 발생한다.")
    @Test
    void save_ExceedSizeException() throws IOException {
        // given
        final MockMultipartFile file = new MockMultipartFile("images",
                "static/images/logo.png",
                "image/png",
                new FileInputStream("./src/test/resources/static/images/logo.png"));

        final List<MultipartFile> files = new ArrayList<>(Collections.nCopies(11, file));

        // when & then
        assertThatThrownBy(() -> imageService.save(files))
                .isInstanceOf(ImageException.class)
                .extracting("code")
                .isEqualTo(5004);
    }

    @DisplayName("Null인 파일이 입력되면 예외가 발생한다.")
    @Test
    void save_NullFileException() throws IOException {
        // given
        final MockMultipartFile file = new MockMultipartFile("images",
                "static/images/logo.png",
                "image/png",
                new FileInputStream("./src/test/resources/static/images/logo.png"));

        final MockMultipartFile nullFile = new MockMultipartFile("images",
                null,
                null,
                nullInputStream());

        final List<MultipartFile> files = List.of(file, nullFile);

        // when & then
        assertThatThrownBy(() -> imageService.save(files))
                .isInstanceOf(ImageException.class)
                .extracting("code")
                .isEqualTo(5002);
    }
}
