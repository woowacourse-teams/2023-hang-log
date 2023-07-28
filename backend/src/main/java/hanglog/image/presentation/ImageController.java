package hanglog.image.presentation;

import hanglog.image.dto.ImageResponse;
import hanglog.image.service.ImageService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ImageResponse> uploadImage(@RequestPart final List<MultipartFile> images) {
        final ImageResponse imageResponse = imageService.save(images);
        final String firstImageUrl = imageResponse.getImageUrls().get(0);
        return ResponseEntity.created(URI.create(firstImageUrl)).body(imageResponse);
    }
}
