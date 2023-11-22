package hanglog.trip.service;

import static hanglog.global.exception.ExceptionCode.*;

import hanglog.global.exception.ImageException;
import hanglog.image.domain.ImageFile;
import hanglog.image.domain.S3ImageEvent;
import hanglog.image.infrastructure.ImageUploader;
import hanglog.trip.dto.response.ImagesResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private static final int MAX_IMAGE_LIST_SIZE = 5;
    private static final int EMPTY_LIST_SIZE = 0;

    private final ImageUploader imageUploader;
    private final ApplicationEventPublisher publisher;

    public ImagesResponse save(final List<MultipartFile> images) {
        long startTime = System.currentTimeMillis();
        MDC.put("time", String.valueOf(startTime));
        validateSizeOfImages(images);
        final List<ImageFile> imageFiles = images.stream()
                .parallel()
                .map(ImageFile::new)
                .toList();
        long endTime = System.currentTimeMillis();

        // 경과 시간 계산
        long elapsedTime = endTime - startTime;
        log.info("이미지 파일 생성 : " +  elapsedTime / 1000.0);
        final List<String> imageNames = uploadImages(imageFiles);

        long endTime1 = System.currentTimeMillis();

        // 경과 시간 계산
        long elapsedTime1 = endTime1 - startTime;
        log.info("이미지 업로드 수행 : " +  elapsedTime1 / 1000.0);

        long endTime2 = System.currentTimeMillis();

        // 경과 시간 계산
        long elapsedTime2 = endTime2 - startTime;

        // 형식화된 시간 출력
        log.info("전체 수행 : " +  elapsedTime2 / 1000.0);
        return new ImagesResponse(imageNames);
    }

    private List<String> uploadImages(final List<ImageFile> imageFiles) {
        try {
            final List<String> uploadedImageNames = imageUploader.uploadImages(imageFiles);
            if(uploadedImageNames.size() != imageFiles.size()) {
                throw new ImageException(INVALID_IMAGE_PATH);
            }
            return uploadedImageNames;
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
