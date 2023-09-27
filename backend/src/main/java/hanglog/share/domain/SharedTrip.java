package hanglog.share.domain;

import static hanglog.global.exception.ExceptionCode.FAIL_SHARE_CODE_HASH;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import hanglog.global.BaseEntity;
import hanglog.global.exception.InvalidDomainException;
import hanglog.trip.domain.Trip;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@AllArgsConstructor
@SQLDelete(sql = "UPDATE shared_trip SET status = 'DELETED' WHERE id = ?")
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "status = 'USABLE'")
@Table(name = "shared_trip", indexes = @Index(name = "ux_shared_trip_shared_code", columnList = "sharedCode", unique = true))
public class SharedTrip extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(nullable = false)
    private String sharedCode;

    public static SharedTrip createdBy(final Trip trip) {
        return new SharedTrip(null, trip, createCode(trip.getId()));
    }

    private static String createCode(final Long tripId) {
        final String tripIdAndDate = String.valueOf(tripId) + LocalDateTime.now();
        try {
            final MessageDigest hashAlgorithm = MessageDigest.getInstance("SHA3-256");
            final byte[] hashBytes = hashAlgorithm.digest(tripIdAndDate.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (final NoSuchAlgorithmException e) {
            throw new InvalidDomainException(FAIL_SHARE_CODE_HASH);
        }
    }

    private static String bytesToHex(final byte[] bytes) {
        return IntStream.range(0, bytes.length)
                .mapToObj(i -> String.format("%02x", bytes[i] & 0xff))
                .collect(Collectors.joining());
    }

    public void changeSharedStatus(final Boolean sharedStatus) {
        this.trip.changeSharedStatus(sharedStatus);
    }

    public Boolean isShared() {
        return this.trip.isShared();
    }
}
