package hanglog.trip.domain;

import static hanglog.global.type.StatusType.USABLE;
import static hanglog.image.util.ImageUrlConverter.convertUrlToName;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import hanglog.global.BaseEntity;
import hanglog.share.domain.SharedTrip;
import hanglog.trip.dto.request.TripUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE trip SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status = 'USABLE'")
public class Trip extends BaseEntity {

    private static final String DEFAULT_IMAGE_NAME = "default-image.png";

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(nullable = false)
    private String imageName;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "trip", cascade = {PERSIST, REMOVE, MERGE}, orphanRemoval = true)
    @OrderBy(value = "ordinal ASC")
    private List<DayLog> dayLogs = new ArrayList<>();

    @OneToOne(mappedBy = "trip", cascade = {PERSIST, REMOVE, MERGE}, orphanRemoval = true)
    private SharedTrip sharedTrip;

    public Trip(
            final Long id,
            final String title,
            final String imageName,
            final LocalDate startDate,
            final LocalDate endDate,
            final String description,
            final SharedTrip sharedTrip,
            final List<DayLog> dayLogs
    ) {
        super(USABLE);
        this.id = id;
        this.title = title;
        this.imageName = imageName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.sharedTrip = sharedTrip;
        this.dayLogs = dayLogs;
    }

    public static Trip of(final String title, final LocalDate startDate, final LocalDate endDate) {
        return new Trip(
                null,
                title,
                DEFAULT_IMAGE_NAME,
                startDate,
                endDate,
                "",
                null,
                new ArrayList<>()
        );
    }

    public void update(final TripUpdateRequest updateRequest) {
        this.title = updateRequest.getTitle();
        this.imageName = updateImageUrl(updateRequest.getImageUrl());
        this.startDate = updateRequest.getStartDate();
        this.endDate = updateRequest.getEndDate();
        this.description = updateRequest.getDescription();
    }

    private String updateImageUrl(final String imageUrl) {
        if (imageUrl == null) {
            return DEFAULT_IMAGE_NAME;
        }
        return convertUrlToName(imageUrl);
    }
}
