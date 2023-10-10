package hanglog.image.domain.repository;

import hanglog.image.domain.Image;
import java.util.List;

public interface CustomImageRepository {

    void saveAll(final List<Image> images);
}
