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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.folder}")
    private String folder;

    public List<String> uploadImages(final List<ImageFile> imageFiles) {
        List<CompletableFuture<String>> imageUploadsFuture = imageFiles.stream()
                .map(imageFile -> CompletableFuture.supplyAsync(() -> uploadImage(imageFile), executorService))
                .toList();
        return getUploadedImageNamesFromFutures(imageUploadsFuture);
    }

    private List<String> getUploadedImageNamesFromFutures(List<CompletableFuture<String>> futures) {
        long startTime = System.currentTimeMillis();
        List<String> fileNames = new ArrayList<>();
        futures.forEach(future -> {
            try {
                fileNames.add(future.join());
            } catch (final CompletionException ignored) {
            }
        });

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        log.info("이미지 업로더 퓨처 조인 경과 시간: " +  elapsedTime / 1000.0);
        return fileNames;
    }

    private String uploadImage(final ImageFile imageFile) {
        log.info("imageUploadThread : " + Thread.currentThread().getName());
        long startTime = System.currentTimeMillis();
        final String path = folder + imageFile.getHashedName();
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(imageFile.getContentType());
        metadata.setContentLength(imageFile.getSize());
        metadata.setCacheControl(CACHE_CONTROL_VALUE);

        try (final InputStream inputStream = imageFile.getInputStream()) {
            s3Client.putObject(bucket, path, inputStream, metadata);
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            log.info("이미지 한 개 업로드 경과 시간: " +  elapsedTime / 1000.0);
        } catch (final AmazonServiceException e) {
            throw new ImageException(INVALID_IMAGE_PATH);
        } catch (final IOException e) {
            throw new ImageException(INVALID_IMAGE);
        }
        return imageFile.getHashedName();
    }
}
