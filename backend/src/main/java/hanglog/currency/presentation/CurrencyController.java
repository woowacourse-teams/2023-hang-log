package hanglog.currency.presentation;

import hanglog.currency.service.CurrencyService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @PostMapping("/today")
    public ResponseEntity<Void> saveTodayCurrency() {
        currencyService.saveDailyCurrency(LocalDate.now());
        return ResponseEntity.ok().build();
    }
}
