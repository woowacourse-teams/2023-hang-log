package hanglog.member.controller;

import hanglog.global.jwt.Auth;
import hanglog.member.dto.request.MyPageRequest;
import hanglog.member.dto.response.MyPageResponse;
import hanglog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PutMapping("/mypage")
    public ResponseEntity<Void> updateMyInfo(
            @Auth final Long memberId,
            @RequestBody MyPageRequest myPageRequest
    ) {
        memberService.updateMyPageInfo(memberId, myPageRequest);
        return ResponseEntity.noContent().build();
    }
}
