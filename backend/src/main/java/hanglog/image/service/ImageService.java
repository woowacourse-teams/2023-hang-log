package hanglog.image.service;

import static hanglog.global.exception.ExceptionCode.IMAGE_LIST_EMPTY;
import static hanglog.global.exception.ExceptionCode.IMAGE_LIST_SIZE_EXCEED;
import static hanglog.global.exception.ExceptionCode.IMAGE_NULL;
import static hanglog.global.exception.ExceptionCode.IMAGE_PATH_INVALID;
import static org.springframework.util.StringUtils.getFilenameExtension;

import hanglog.global.exception.ImageException;
import hanglog.image.dto.ImagesResponse;
import hanglog.image.util.ImageNameUrlConverter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private static final String EXTENSION_DELIMITER = ".";
    private static final int MAX_IMAGE_LIST_SIZE = 10;
    private static final int EMPTY_LIST_SIZE = 0;

    @Value("${image.base-path}")
    private String imageBasePath;

    public ImagesResponse save(final List<MultipartFile> images) {
        validateImagesSize(images);
        validateImageNull(images);

        makeDirectoryIfNotExist();

        final List<String> hashedNames = images.stream()
                .map(this::hashName)
                .toList();

        final List<Path> imagePaths = hashedNames.stream()
                .map(hashedName -> Path.of(imageBasePath, hashedName))
                .toList();

        uploadImages(images, imagePaths);

        final List<String> imageUrls = hashedNames.stream()
                .map(ImageNameUrlConverter::convertNameToUrl)
                .toList();

        return new ImagesResponse(imageUrls);
    }

    private void validateImageNull(final List<MultipartFile> images) {
        images.forEach(image -> {
            if (image.isEmpty()) {
                throw new ImageException(IMAGE_NULL);
            }
        });
    }

    private void validateImagesSize(final List<MultipartFile> images) {
        if (images.size() > MAX_IMAGE_LIST_SIZE) {
            throw new ImageException(IMAGE_LIST_SIZE_EXCEED);
        }
        if (images.size() == EMPTY_LIST_SIZE) {
            throw new ImageException(IMAGE_LIST_EMPTY);
        }
    }

    private void uploadImages(final List<MultipartFile> images, final List<Path> imagePaths) {
        try {
            for (int i = 0; i < images.size(); i++) {
                images.get(i).transferTo(imagePaths.get(i));
            }
        } catch (final IOException e) {
            throw new ImageException(IMAGE_PATH_INVALID);
        }
    }

    private void makeDirectoryIfNotExist() {
        final File directory = new File(imageBasePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private String hashName(final MultipartFile image) {
        final String name = image.getOriginalFilename();
        final String filenameExtension = EXTENSION_DELIMITER + getFilenameExtension(name);
        final String nameAndDate = name + LocalDateTime.now();
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            final byte[] hashbytes = digest.digest(nameAndDate.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashbytes) + filenameExtension;
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String bytesToHex(final byte[] bytes) {
        final StringBuilder sb = new StringBuilder();
        for (final byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
