package hanglog.admin.domain.repository;

import hanglog.admin.domain.AdminMember;
import hanglog.admin.domain.type.AdminType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMemberRepository extends JpaRepository<AdminMember, Long> {

    Optional<AdminMember> findAdminMemberByUserName(String userName);

    Boolean existsAdminMemberByIdAndAdminType(Long id, AdminType adminType);

    Boolean existsAdminMemberByUserName(String userName);
}
