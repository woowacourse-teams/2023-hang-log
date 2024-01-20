package hanglog.admin.presentation;


import static org.springframework.data.domain.Sort.Direction.DESC;

import hanglog.auth.AdminAuth;
import hanglog.auth.AdminOnly;
import hanglog.auth.domain.Accessor;
import hanglog.currency.dto.request.CurrencyRequest;
import hanglog.currency.dto.response.CurrencyListResponse;
import hanglog.currency.service.CurrencyService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/currencies")
public class AdminCurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    @AdminOnly
    public ResponseEntity<CurrencyListResponse> getCurrencies(
            @AdminAuth final Accessor accessor,
            @PageableDefault(sort = "date", direction = DESC) final Pageable pageable
    ) {
        final CurrencyListResponse currencies = currencyService.getCurrenciesByPage(pageable);
        return ResponseEntity.ok().body(currencies);
    }

    @PostMapping
    @AdminOnly
    public ResponseEntity<Void> createCurrency(
            @AdminAuth final Accessor accessor,
            @RequestBody @Valid final CurrencyRequest currencyRequest
    ) {
        final Long currencyId = currencyService.save(currencyRequest);
        return ResponseEntity.created(URI.create("/admin/currencies/" + currencyId)).build();
    }
}
