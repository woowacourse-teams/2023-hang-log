package hanglog.image.domain.repository;

import hanglog.image.domain.Image;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomImageRepositoryImpl implements CustomImageRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void saveAll(final List<Image> images) {
        final String sql = """
                    INSERT INTO image (created_at, modified_at, item_id, name, status) 
                    VALUES (:createdAt, :modifiedAt, :itemId, :name, :status)
                """;
        namedParameterJdbcTemplate.batchUpdate(sql, getImageToSqlParameterSources(images));
    }

    @Override
    public void deleteAll(final List<Image> images) {
        final String sql = """
                    DELETE FROM image WHERE id IN (:imageIds)
                """;
        namedParameterJdbcTemplate.update(sql, getDeletedImageIdsSqlParameterSources(images));
    }

    private SqlParameterSource getDeletedImageIdsSqlParameterSources(final List<Image> images) {
        final List<Long> imageIds = images.stream()
                .map(Image::getId)
                .toList();
        return new MapSqlParameterSource("imageIds", imageIds);
    }

    private MapSqlParameterSource[] getImageToSqlParameterSources(final List<Image> images) {
        return images.stream()
                .map(this::getImageToSqlParameterSource)
                .toArray(MapSqlParameterSource[]::new);
    }

    private MapSqlParameterSource getImageToSqlParameterSource(final Image image) {
        final LocalDateTime now = LocalDateTime.now();
        return new MapSqlParameterSource()
                .addValue("createdAt", now)
                .addValue("modifiedAt", now)
                .addValue("itemId", image.getItem().getId())
                .addValue("name", image.getName())
                .addValue("status", image.getStatus().name());
    }
}
