package hanglog.image.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ImageEventListener {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @EventListener
    public void delete(final ImageEvent event) {
        final DeleteObjectRequest request = new DeleteObjectRequest(bucket, event.getName());
        s3Client.deleteObject(request);
    }
}
