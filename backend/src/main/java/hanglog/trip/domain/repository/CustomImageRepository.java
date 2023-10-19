package hanglog.trip.domain.repository;

import hanglog.trip.domain.Image;
import java.util.List;

public interface CustomImageRepository {

    void saveAll(final List<Image> images);

    void deleteAll(final List<Image> images);
}
