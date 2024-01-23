package hanglog.admin.service;


import static hanglog.global.exception.ExceptionCode.DUPLICATED_ADMIN_USERNAME;
import static hanglog.global.exception.ExceptionCode.INVALID_CURRENT_PASSWORD;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_ADMIN_ID;

import hanglog.admin.domain.AdminMember;
import hanglog.admin.domain.repository.AdminMemberRepository;
import hanglog.admin.domain.type.AdminType;
import hanglog.admin.dto.request.AdminMemberCreateRequest;
import hanglog.admin.dto.request.PasswordUpdateRequest;
import hanglog.admin.dto.response.AdminMemberResponse;
import hanglog.admin.infrastructure.PasswordEncoder;
import hanglog.global.exception.AdminException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminMemberService {

    private final AdminMemberRepository adminMemberRepository;
    private final PasswordEncoder passwordEncoder;

    public List<AdminMemberResponse> getAdminMembers() {
        final List<AdminMember> adminMembers = adminMemberRepository.findAll();
        return adminMembers.stream()
                .map(AdminMemberResponse::from)
                .toList();
    }

    public Long createAdminMember(final AdminMemberCreateRequest request) {
        if (adminMemberRepository.existsByUserName(request.getUserName())) {
            throw new AdminException(DUPLICATED_ADMIN_USERNAME);
        }

        return adminMemberRepository.save(new AdminMember(request.getUserName(),
                        passwordEncoder.encode(request.getPassword()),
                        AdminType.getMappedAdminType(request.getAdminType())))
                .getId();
    }

    public void updatePassword(final Long adminMemberId, final PasswordUpdateRequest request) {
        final AdminMember adminMember = adminMemberRepository.findById(adminMemberId)
                .orElseThrow(() -> new AdminException(NOT_FOUND_ADMIN_ID));

        if (!passwordEncoder.matches(request.getCurrentPassword(), adminMember.getPassword())) {
            throw new AdminException(INVALID_CURRENT_PASSWORD);
        }

        final AdminMember updatedAdminMember = new AdminMember(
                adminMember.getId(),
                adminMember.getUserName(),
                passwordEncoder.encode(request.getNewPassword()),
                adminMember.getAdminType()
        );
        adminMemberRepository.save(updatedAdminMember);
    }
}
