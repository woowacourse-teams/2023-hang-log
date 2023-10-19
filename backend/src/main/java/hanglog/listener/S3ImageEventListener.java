package hanglog.listener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import com.amazonaws.services.s3.AmazonS3;
import hanglog.image.domain.S3ImageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
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
        if (imageName.equals("default-image.png")) {
            return;
        }
        s3Client.deleteObject(bucket, folder + imageName);
    }
}
