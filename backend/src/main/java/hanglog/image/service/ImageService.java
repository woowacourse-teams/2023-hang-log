package hanglog.image.service;

import static hanglog.global.exception.ExceptionCode.EMPTY_IMAGE_LIST;
import static hanglog.global.exception.ExceptionCode.EXCEED_IMAGE_LIST_SIZE;

import hanglog.global.exception.ImageException;
import hanglog.image.dto.ImagesResponse;
import hanglog.image.util.ImageUploader;
import hanglog.image.util.ImageUrlConverter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final int MAX_IMAGE_LIST_SIZE = 5;
    private static final int EMPTY_LIST_SIZE = 0;

    private final ImageUploader imageUploader;
    private final ImageUrlConverter imageUrlConverter;

    public ImagesResponse saveImages(final List<MultipartFile> images) {
        validateSizeOfImages(images);

        final List<String> imageUrls = imageUploader.save(images).stream()
                .map(imageUrlConverter::convertNameToUrl)
                .toList();

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
}
