package hanglog.admin.domain.repository;

import hanglog.admin.domain.AdminMember;
import hanglog.admin.domain.type.AdminType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMemberRepository extends JpaRepository<AdminMember, Long> {

    Optional<AdminMember> findByUserName(String userName);

    Boolean existsByIdAndAdminType(Long id, AdminType adminType);

    Boolean existsByUserName(String userName);
}
