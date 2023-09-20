package hanglog.auth.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class RefreshToken {

    @Id
    private String token;

    @Column(nullable = false)
    private Long memberId;

    public RefreshToken(final String token, final Long memberId) {
        this.token = token;
        this.memberId = memberId;
    }
}
