package hanglog.trip.domain.picture.repository;

import hanglog.trip.domain.picture.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
