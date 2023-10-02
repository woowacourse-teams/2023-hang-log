package hanglog.image.dto;

import hanglog.image.domain.Image;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImagesResponse {

    private List<String> imageNames;

    public static ImagesResponse of(final List<Image> images) {
        return new ImagesResponse(
                images.stream()
                        .map(Image::getName)
                        .toList());
    }
}
