package hanglog.member.controller;

import hanglog.global.jwt.Auth;
import hanglog.member.dto.MyPageResponse;
import hanglog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    public final MemberService memberService;

    @GetMapping("/mypage")
    public ResponseEntity<MyPageResponse> getMyInfo(@Auth final Long memberId) {
        final MyPageResponse myPageResponse = memberService.getMyPageInfo(memberId);
        return ResponseEntity.ok().body(myPageResponse);
    }
}
