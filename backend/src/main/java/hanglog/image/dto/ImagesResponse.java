package hanglog.image.dto;

import static hanglog.image.util.ImageNameUrlConverter.convertNameToUrl;

import hanglog.image.domain.Image;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImagesResponse {

    private List<String> imageUrls;

    public static ImagesResponse of(final List<Image> images) {
        return new ImagesResponse(
                images.stream()
                        .map(image -> convertNameToUrl(image.getImageName()))
                        .toList());
    }
}
