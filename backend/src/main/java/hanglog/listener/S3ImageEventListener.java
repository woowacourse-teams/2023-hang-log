package hanglog.listener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import hanglog.image.domain.S3ImageEvent;
import hanglog.image.domain.S3ImagesEvent;
import hanglog.image.domain.S3ImagesEventForDeleteObjects;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3ImageEventListener {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.folder}")
    private String folder;

    @Async
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener(fallbackExecution = true)
    public void deleteImageFileInS3(final S3ImageEvent event) {
        final String imageName = event.getImageName();
        s3Client.deleteObject(bucket, folder + imageName);
    }

    @Async
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener(fallbackExecution = true)
    public void deleteImageFilesInS3(final S3ImagesEvent event) {
        final List<String> imageNames = event.getImageNames();

        final List<CompletableFuture<Void>> deleteFutures = imageNames.stream()
                .map(imageName -> CompletableFuture.runAsync(() -> s3Client.deleteObject(bucket, folder + imageName)))
                .toList();

        final long startTime1 = System.currentTimeMillis(); // Start time measurement
        final CompletableFuture<Void> allOf = CompletableFuture.allOf(deleteFutures.toArray(new CompletableFuture[0]));

        final long startTime = System.currentTimeMillis(); // Start time measurement

        try {
            allOf.get(); // Wait for all delete operations to complete
        } catch (final Exception e) {
            // Handle exceptions if needed
            throw new RuntimeException("Failed to delete one or more image files in S3", e);
        }

        final long endTime = System.currentTimeMillis(); // End time measurement
        final long executionTime = endTime - startTime;
        final long executionTime1 = endTime - startTime1;
        log.info("CompletableFuture 실행 시간1 : " + executionTime1);
        log.info("CompletableFuture 실행 시간 : " + executionTime);
        // imageNames.forEach(imageName -> CompletableFuture.runAsync(() -> s3Client.deleteObject(bucket, folder + imageName)));
    }

    @Async
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener(fallbackExecution = true)
    public void deleteImageFilesByDeleteObjects(final S3ImagesEventForDeleteObjects event) {
        final List<KeyVersion> imageKeyVersions = event.getImageNames().stream()
                .map(imageName -> new KeyVersion(folder + imageName))
                .toList();
        final DeleteObjectsRequest deleteImageRequest = new DeleteObjectsRequest(bucket).withKeys(imageKeyVersions);
        final long startTime = System.currentTimeMillis(); // 시작 시간 측정
        s3Client.deleteObjects(deleteImageRequest);
        final long endTime = System.currentTimeMillis(); // 종료 시간 측정
        final long executionTime = endTime - startTime;
        log.info("deleteObjects 실행 시간 : " + executionTime);
    }
}
