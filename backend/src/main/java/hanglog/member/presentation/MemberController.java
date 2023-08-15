package hanglog.member.presentation;

import hanglog.auth.Auth;
import hanglog.member.dto.request.MyPageRequest;
import hanglog.member.dto.response.MyPageResponse;
import hanglog.member.service.MemberService;
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
    public ResponseEntity<MyPageResponse> getMyInfo(@Auth final Long memberId) {
        final MyPageResponse myPageResponse = memberService.getMyPageInfo(memberId);
        return ResponseEntity.ok().body(myPageResponse);
    }

    @PutMapping
    public ResponseEntity<Void> updateMyInfo(
            @Auth final Long memberId,
            @RequestBody final MyPageRequest myPageRequest
    ) {
        memberService.updateMyPageInfo(memberId, myPageRequest);
        return ResponseEntity.noContent().build();
    }
}
