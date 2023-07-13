package hanglog.member;

import static hanglog.global.type.StatusType.USABLE;
import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import hanglog.global.type.StatusType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

// todo: status 고민하기
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    private LocalDateTime lastLoginDate;
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private StatusType status = USABLE;
    @Enumerated(value = EnumType.STRING)
    private MemberState state;

    public Member(Long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}
