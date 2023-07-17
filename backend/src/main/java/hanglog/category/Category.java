package hanglog.category;

import static lombok.AccessLevel.PROTECTED;

import hanglog.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Category extends BaseEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;
}
