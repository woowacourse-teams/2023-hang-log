package hanglog.member.presentation;

import hanglog.auth.Auth;
import hanglog.auth.domain.Accessor;
import hanglog.member.dto.request.MyPageRequest;
import hanglog.member.dto.response.MyPageResponse;
import hanglog.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("mypage")
public class MemberController {

    public final MemberService memberService;

    @GetMapping
    public ResponseEntity<MyPageResponse> getMyInfo(@Auth final Accessor accessor) {
        final MyPageResponse myPageResponse = memberService.getMyPageInfo(accessor.getMemberId());
        return ResponseEntity.ok().body(myPageResponse);
    }

    @PutMapping
    public ResponseEntity<Void> updateMyInfo(
            @Auth final Accessor accessor,
            @RequestBody @Valid final MyPageRequest myPageRequest
    ) {
        memberService.updateMyPageInfo(accessor.getMemberId(), myPageRequest);
        return ResponseEntity.noContent().build();
    }
}
