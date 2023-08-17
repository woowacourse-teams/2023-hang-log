package hanglog.member.domain;

import static hanglog.member.domain.MemberState.ACTIVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE member SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status = 'ACTIVE'")
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

    @Enumerated(value = STRING)
    private MemberState status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public Member(final Long id, final String socialLoginId, final String nickname, final String imageUrl) {
        this.id = id;
        this.socialLoginId = socialLoginId;
        this.nickname = nickname;
        this.lastLoginDate = LocalDateTime.now();
        this.imageUrl = imageUrl;
        this.status = ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public Member(final String socialLoginId, final String nickname, final String imageUrl) {
        this(null, socialLoginId, nickname, imageUrl);
    }
}
