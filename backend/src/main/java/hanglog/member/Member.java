package hanglog.member;

import static hanglog.member.MemberState.ACTIVE;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String socialLoginId;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false)
    private LocalDateTime lastLoginDate;

    @Column(nullable = false)
    private String imageUrl;

    @Enumerated(value = EnumType.STRING)
    private MemberState status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public Member(final String socialLoginId, final String nickname, final String imageUrl) {
        this.socialLoginId = socialLoginId;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.lastLoginDate = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.status = ACTIVE;
    }
}
