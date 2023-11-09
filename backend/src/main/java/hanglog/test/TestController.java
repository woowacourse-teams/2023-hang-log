package hanglog.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/legend")
    public ResponseEntity<String> legendTest() {
        long startTime = System.currentTimeMillis();
        testService.legend();
        long endTime = System.currentTimeMillis();
        return ResponseEntity.ok().body("소요 시간 = " + (double)(endTime - startTime) / 1000 + " sec");
    }
}
