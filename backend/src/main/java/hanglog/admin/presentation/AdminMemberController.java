package hanglog.admin.presentation;

import hanglog.admin.dto.request.AdminMemberCreateRequest;
import hanglog.admin.dto.request.PasswordUpdateRequest;
import hanglog.admin.dto.response.AdminMemberResponse;
import hanglog.admin.service.AdminMemberService;
import hanglog.auth.AdminAuth;
import hanglog.auth.MasterOnly;
import hanglog.auth.domain.Accessor;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/members")
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    @GetMapping
    @MasterOnly
    public ResponseEntity<List<AdminMemberResponse>> getAdminMembers(
            @AdminAuth final Accessor accessor
    ) {
        final List<AdminMemberResponse> adminMemberResponses = adminMemberService.getAdminMembers();
        return ResponseEntity.ok(adminMemberResponses);
    }

    @PostMapping
    @MasterOnly
    public ResponseEntity<Void> createAdminMember(
            @AdminAuth final Accessor accessor,
            @RequestBody @Valid final AdminMemberCreateRequest adminMemberCreateRequest
    ) {
        final Long memberId = adminMemberService.createAdminMember(adminMemberCreateRequest);
        return ResponseEntity.created(URI.create("/admin/members/" + memberId)).build();
    }

    @PatchMapping("/{memberId}/password")
    @MasterOnly
    public ResponseEntity<Void> updatePassword(
            @AdminAuth final Accessor accessor,
            @PathVariable final Long memberId,
            @RequestBody @Valid final PasswordUpdateRequest passwordUpdateRequest
    ) {
        adminMemberService.updatePassword(memberId, passwordUpdateRequest);
        return ResponseEntity.noContent().build();
    }
}
