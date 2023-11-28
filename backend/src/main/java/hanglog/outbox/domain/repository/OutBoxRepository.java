package hanglog.outbox.domain.repository;

import hanglog.outbox.domain.OutBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OutBoxRepository extends JpaRepository<OutBox, Long> {

    @Modifying
    @Query("""
            UPDATE OutBox  outBox
            SET outBox.status = 'DELETED'
            WHERE outBox.targetId = :targetId
            """)
    void deleteByTargetId(@Param("targetId") final Long targetId);
}
