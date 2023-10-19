package hanglog.category.domain;

import static lombok.AccessLevel.PROTECTED;

import hanglog.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE category SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status = 'USABLE'")
public class Category extends BaseEntity {

    @Id
    private Long id;

    @Column(nullable = false, length = 50)
    private String engName;

    @Column(nullable = false, length = 50)
    private String korName;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category category)) {
            return false;
        }
        return Objects.equals(id, category.id) && Objects.equals(engName, category.engName)
                && Objects.equals(korName, category.korName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, engName, korName);
    }
}
