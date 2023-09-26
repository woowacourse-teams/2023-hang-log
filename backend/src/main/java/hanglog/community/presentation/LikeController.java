package hanglog.community.presentation;

import hanglog.auth.Auth;
import hanglog.auth.MemberOnly;
import hanglog.auth.domain.Accessor;
import hanglog.community.dto.request.LikeRequest;
import hanglog.community.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class LikeController {

    private final LikeService likeService;

    @MemberOnly
    @PostMapping("/trips/{tripId}/like")
    public ResponseEntity<Void> updateLikeStatus(
            @Auth final Accessor accessor,
            @PathVariable final Long tripId,
            @RequestBody final LikeRequest likeRequest
    ) {
        likeService.update(accessor.getMemberId(), tripId, likeRequest);
        return ResponseEntity.noContent().build();
    }
}
