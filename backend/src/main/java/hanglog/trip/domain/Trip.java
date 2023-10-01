package hanglog.trip.domain;

import static hanglog.global.type.StatusType.USABLE;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import hanglog.community.domain.type.PublishedStatusType;
import hanglog.global.BaseEntity;
import hanglog.member.domain.Member;
import hanglog.share.domain.SharedTrip;
import hanglog.share.domain.type.SharedStatusType;
import hanglog.trip.dto.request.TripUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

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

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private PublishedStatusType publishedStatus;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private SharedStatusType sharedStatus;

    @OneToMany(mappedBy = "trip", cascade = {PERSIST, REMOVE, MERGE}, orphanRemoval = true)
    @OrderBy(value = "ordinal ASC")
    private List<DayLog> dayLogs = new ArrayList<>();

    @OneToOne(mappedBy = "trip", cascade = {PERSIST, REMOVE, MERGE}, orphanRemoval = true)
    private SharedTrip sharedTrip;

    public Trip(
            final Long id,
            final Member member,
            final String title,
            final String imageName,
            final LocalDate startDate,
            final LocalDate endDate,
            final String description,
            final SharedTrip sharedTrip,
            final List<DayLog> dayLogs,
            final SharedStatusType sharedStatus,
            final PublishedStatusType publishedStatus
    ) {
        super(USABLE);
        this.id = id;
        this.member = member;
        this.title = title;
        this.imageName = imageName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.sharedTrip = sharedTrip;
        this.dayLogs = dayLogs;
        this.sharedStatus = sharedStatus;
        this.publishedStatus = publishedStatus;
    }

    public static Trip of(final Member member, final String title, final String imageName, final LocalDate startDate,
                          final LocalDate endDate) {
        return new Trip(
                null,
                member,
                title,
                imageName,
                startDate,
                endDate,
                "",
                null,
                new ArrayList<>(),
                SharedStatusType.UNSHARED,
                PublishedStatusType.UNPUBLISHED
        );
    }

    public void update(final TripUpdateRequest updateRequest, final String imageUrl) {
        this.title = updateRequest.getTitle();
        this.imageName = imageUrl;
        this.startDate = updateRequest.getStartDate();
        this.endDate = updateRequest.getEndDate();
        this.description = updateRequest.getDescription();
    }

    public Optional<String> getSharedCode() {
        if (Optional.ofNullable(sharedTrip).isEmpty()) {
            return Optional.empty();
        }
        if (!isShared()) {
            return Optional.empty();
        }
        return Optional.of(sharedTrip.getSharedCode());
    }

    public boolean isShared() {
        return this.sharedStatus.equals(SharedStatusType.SHARED);
    }

    public void changeSharedStatus(final Boolean isShared) {
        this.sharedStatus = SharedStatusType.mappingType(isShared);
    }

    public boolean isPublished() {
        return this.publishedStatus.equals(PublishedStatusType.PUBLISHED);
    }

    public void changePublishedStatus(final Boolean isPublished) {
        this.publishedStatus = PublishedStatusType.mappingType(isPublished);
    }

    public Boolean isWriter(final Long memberId) {
        return this.member.getId().equals(memberId);
    }
}
