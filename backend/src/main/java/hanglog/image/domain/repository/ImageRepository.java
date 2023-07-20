package hanglog.image.domain.repository;

import hanglog.image.domain.Image;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByImageUrl(String imageUrl);
}
