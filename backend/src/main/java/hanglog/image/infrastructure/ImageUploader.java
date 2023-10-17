package hanglog.image.infrastructure;

import static hanglog.global.exception.ExceptionCode.INVALID_IMAGE;
import static hanglog.global.exception.ExceptionCode.INVALID_IMAGE_PATH;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import hanglog.global.exception.ImageException;
import hanglog.image.domain.ImageFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImageUploader {

    private static final String CACHE_CONTROL_VALUE = "max-age=3153600";

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.folder}")
    private String folder;

    public List<String> uploadImages(final List<ImageFile> imageFiles) {
        return imageFiles.stream()
                .map(this::uploadImage)
                .toList();
    }

    private String uploadImage(final ImageFile imageFile) {
        for (int i = 1; i <= 100; i++) {
            final String testPath = folder + i + imageFile.getHashedName();
            final ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(imageFile.getContentType());
            metadata.setContentLength(imageFile.getSize());
            metadata.setCacheControl(CACHE_CONTROL_VALUE);
            log.info("[이미지 업로드 준비] " + testPath);
            try (final InputStream inputStream = imageFile.getInputStream()) {
                s3Client.putObject(bucket, testPath, inputStream, metadata);
                log.info("[이미지 업로드 완료] " + testPath);
            } catch (final AmazonServiceException e) {
                throw new ImageException(INVALID_IMAGE_PATH);
            } catch (final IOException e) {
                throw new ImageException(INVALID_IMAGE);
            }
        }
        final String path = folder + imageFile.getHashedName();
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(imageFile.getContentType());
        metadata.setContentLength(imageFile.getSize());
        metadata.setCacheControl(CACHE_CONTROL_VALUE);

        try (final InputStream inputStream = imageFile.getInputStream()) {
            s3Client.putObject(bucket, path, inputStream, metadata);
        } catch (final AmazonServiceException e) {
            throw new ImageException(INVALID_IMAGE_PATH);
        } catch (final IOException e) {
            throw new ImageException(INVALID_IMAGE);
        }
        return imageFile.getHashedName();
    }

//    private String uploadImage(final ImageFile imageFile) {
//        final String path = folder + imageFile.getHashedName();
//        final ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentType(imageFile.getContentType());
//        metadata.setContentLength(imageFile.getSize());
//        metadata.setCacheControl(CACHE_CONTROL_VALUE);
//
//        try (final InputStream inputStream = imageFile.getInputStream()) {
//            s3Client.putObject(bucket, path, inputStream, metadata);
//        } catch (final AmazonServiceException e) {
//            throw new ImageException(INVALID_IMAGE_PATH);
//        } catch (final IOException e) {
//            throw new ImageException(INVALID_IMAGE);
//        }
//        return imageFile.getHashedName();
//    }
}
