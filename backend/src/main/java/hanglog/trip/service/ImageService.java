package hanglog.trip.service;

import static hanglog.global.exception.ExceptionCode.EMPTY_IMAGE_LIST;
import static hanglog.global.exception.ExceptionCode.EXCEED_IMAGE_LIST_SIZE;

import hanglog.global.exception.ImageException;
import hanglog.image.domain.ImageFile;
import hanglog.image.domain.S3ImageEvent;
import hanglog.image.dto.ImagesResponse;
import hanglog.image.infrastructure.ImageUploader;
import hanglog.trip.dto.response.ImagesResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final int MAX_IMAGE_LIST_SIZE = 5;
    private static final int EMPTY_LIST_SIZE = 0;

    private final ImageUploader imageUploader;
    private final ApplicationEventPublisher publisher;

    public ImagesResponse save(final List<MultipartFile> images) {
        validateSizeOfImages(images);
        final List<ImageFile> imageFiles = images.stream()
                .map(ImageFile::new)
                .toList();
        final List<String> imageNames = uploadImages(imageFiles);
        return new ImagesResponse(imageNames);
    }

    private List<String> uploadImages(final List<ImageFile> imageFiles) {
        try {
            return imageUploader.uploadImages(imageFiles);
        } catch (final ImageException e) {
            imageFiles.forEach(imageFile -> publisher.publishEvent(new S3ImageEvent(imageFile.getHashedName())));
            throw e;
        }
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
