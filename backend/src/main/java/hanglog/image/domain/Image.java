package hanglog.image.domain;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import hanglog.global.BaseEntity;
import hanglog.trip.domain.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String imageName;

    @ManyToOne(fetch = LAZY, cascade = {PERSIST})
    @JoinColumn(name = "item_id")
    private Item item;

    public Image(final String imageName, final Item item) {
        this.imageName = imageName;
        this.item = item;
    }

    public Image(final String imageName) {
        this(imageName, null);
    }
}
