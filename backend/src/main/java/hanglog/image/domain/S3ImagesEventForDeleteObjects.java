package hanglog.image.domain;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class S3ImagesEventForDeleteObjects {

    private final List<String> imageNames;
}
