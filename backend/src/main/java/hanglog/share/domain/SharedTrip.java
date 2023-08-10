package hanglog.share.domain;

import static hanglog.global.exception.ExceptionCode.FAIL_IMAGE_NAME_HASH;
import static hanglog.share.domain.type.SharedStatusType.SHARED;
import static jakarta.persistence.EnumType.STRING;

import hanglog.global.exception.ImageException;
import hanglog.share.domain.type.SharedStatusType;
import hanglog.share.dto.request.TripSharedStatusRequest;
import hanglog.trip.domain.Trip;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SharedTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(nullable = false)
    private String shareCode;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private SharedStatusType sharedStatus;

    private SharedTrip(final Trip trip, final String shareCode, final SharedStatusType sharedStatus) {
        this.trip = trip;
        this.shareCode = shareCode;
        this.sharedStatus = sharedStatus;
    }

    public static SharedTrip createdBy(final Trip trip) {
        return new SharedTrip(trip, createCode(trip.getId()), SHARED);
    }

    private static String createCode(final Long tripId) {
        final String tripIdAndDate = String.valueOf(tripId) + LocalDateTime.now();
        try {
            final MessageDigest hashAlgorithm = MessageDigest.getInstance("SHA3-256");
            final byte[] hashBytes = hashAlgorithm.digest(tripIdAndDate.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (final NoSuchAlgorithmException e) {
            throw new ImageException(FAIL_IMAGE_NAME_HASH);
        }
    }

    private static String bytesToHex(final byte[] bytes) {
        return IntStream.range(0, bytes.length)
                .mapToObj(i -> String.format("%02x", bytes[i] & 0xff))
                .collect(Collectors.joining());
    }

    public void updateSharedStatus(final TripSharedStatusRequest tripSharedStatusRequest) {
        this.sharedStatus = SharedStatusType.mappingType(tripSharedStatusRequest.getSharedStatus());
    }
}
