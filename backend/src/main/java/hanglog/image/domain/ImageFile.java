package hanglog.image.domain;

import static hanglog.global.exception.ExceptionCode.FAIL_IMAGE_NAME_HASH;
import static hanglog.global.exception.ExceptionCode.INVALID_IMAGE_PATH;
import static hanglog.global.exception.ExceptionCode.NULL_IMAGE;
import static org.springframework.util.StringUtils.getFilenameExtension;

import hanglog.global.exception.ImageException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ImageFile {

    private static final String EXTENSION_DELIMITER = ".";

    private final MultipartFile file;
    private final String hashedName;

    public ImageFile(final MultipartFile file) {
        validateNullImage(file);
        this.file = file;
        this.hashedName = hashName(file);
    }

    private void validateNullImage(final MultipartFile file) {
        if (file.isEmpty()) {
            throw new ImageException(NULL_IMAGE);
        }
    }

    private String hashName(final MultipartFile image) {
        final String name = image.getOriginalFilename();
        final String filenameExtension = EXTENSION_DELIMITER + getFilenameExtension(name);
        final String nameAndDate = name + LocalDateTime.now();
        try {
            final MessageDigest hashAlgorithm = MessageDigest.getInstance("SHA3-256");
            final byte[] hashBytes = hashAlgorithm.digest(nameAndDate.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes) + filenameExtension;
        } catch (final NoSuchAlgorithmException e) {
            throw new ImageException(FAIL_IMAGE_NAME_HASH);
        }
    }

    private String bytesToHex(final byte[] bytes) {
        return IntStream.range(0, bytes.length)
                .mapToObj(i -> String.format("%02x", bytes[i] & 0xff))
                .collect(Collectors.joining());
    }

    public String getContentType() {
        return this.file.getContentType();
    }

    public long getSize() {
        return this.file.getSize();
    }

    public InputStream getInputStream() throws IOException {
        return this.file.getInputStream();
    }
}
