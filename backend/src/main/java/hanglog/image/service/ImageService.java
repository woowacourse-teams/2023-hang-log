package hanglog.image.service;

import static hanglog.global.exception.ExceptionCode.EMPTY_IMAGE_LIST;
import static hanglog.global.exception.ExceptionCode.EXCEED_IMAGE_LIST_SIZE;
import static hanglog.image.util.ImageUrlConverter.convertNameToUrl;

import hanglog.global.exception.ImageException;
import hanglog.image.domain.ImageFile;
import hanglog.image.dto.ImagesResponse;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private static final int MAX_IMAGE_LIST_SIZE = 10;
    private static final int EMPTY_LIST_SIZE = 0;

    @Value("${image.base-path}")
    private String imageBasePath;

    public ImagesResponse save(final List<MultipartFile> images) {
        validateSizeOfImages(images);

        makeDirectoryIfNotExist();

        final List<ImageFile> imageFiles = images.stream()
                .map(ImageFile::new)
                .toList();

        final List<String> imageUrls = uploadImages(imageFiles);

        return new ImagesResponse(imageUrls);
    }

    private void validateSizeOfImages(final List<MultipartFile> images) {
        if (images.size() > MAX_IMAGE_LIST_SIZE) {
            throw new ImageException(EXCEED_IMAGE_LIST_SIZE);
        }
        if (images.size() == EMPTY_LIST_SIZE) {
            throw new ImageException(EMPTY_IMAGE_LIST);
        }
    }


    private void makeDirectoryIfNotExist() {
        final File directory = new File(imageBasePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private List<String> uploadImages(final List<ImageFile> imageFiles) {
        return imageFiles.stream()
                .map(this::uploadImage)
                .toList();
    }

    private String uploadImage(final ImageFile imageFile) {
        final Path imagePath = Path.of(imageBasePath, imageFile.getHashedName());
        imageFile.transferTo(imagePath);
        return convertNameToUrl(imageFile.getHashedName());
    }
}
