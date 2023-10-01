package hanglog.image.service;

import static hanglog.global.exception.ExceptionCode.EMPTY_IMAGE_LIST;
import static hanglog.global.exception.ExceptionCode.EXCEED_IMAGE_LIST_SIZE;
import static hanglog.global.exception.ExceptionCode.INVALID_IMAGE;
import static hanglog.global.exception.ExceptionCode.INVALID_IMAGE_PATH;
import static hanglog.image.util.ImageUrlConverter.convertNameToUrl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import hanglog.global.exception.ImageException;
import hanglog.image.domain.ImageFile;
import hanglog.image.dto.ImagesResponse;
import hanglog.image.util.ImageUrlConverter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {
    private static final int MAX_IMAGE_LIST_SIZE = 5;
    private static final int EMPTY_LIST_SIZE = 0;
    private static final String CACHE_CONTROL_VALUE = "max-age=3153600";

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.folder}")
    private String folder;

    public ImagesResponse save(final List<MultipartFile> images) {
        validateSizeOfImages(images);
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

    private List<String> uploadImages(final List<ImageFile> imageFiles) {
        return imageFiles.stream()
                .map(this::uploadImage)
                .toList();
    }

    private String uploadImage(final ImageFile imageFile) {
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
        return convertNameToUrl(imageFile.getHashedName());
    }

    private void deleteImage(final String imagePath) {
        final String imageName = ImageUrlConverter.convertUrlToName(imagePath);
        final DeleteObjectRequest request = new DeleteObjectRequest(bucket, imageName);
        s3Client.deleteObject(request);
    }
}
