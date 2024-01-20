package hanglog.currency.dto.request;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@AllArgsConstructor
public class CurrencyRequest {

    @NotNull(message = "날짜를 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    @NotNull(message = "USD 금액을 입력해주세요.")
    @DecimalMin(value = "0", message = "USD 금액이 0원보다 작을 수 없습니다.")
    @DecimalMax(value = "100000", message = "USD 금액이 10만원보다 클 수 없습니다.")
    private final double usd;

    @NotNull(message = "EUR 금액을 입력해주세요.")
    @DecimalMin(value = "0", message = "EUR 금액이 0원보다 작을 수 없습니다.")
    @DecimalMax(value = "100000", message = "EUR 금액이 10만원보다 클 수 없습니다.")
    private final double eur;

    @NotNull(message = "GBP 금액을 입력해주세요.")
    @DecimalMin(value = "0", message = "GBP 금액이 0원보다 작을 수 없습니다.")
    @DecimalMax(value = "100000", message = "GBP 금액이 10만원보다 클 수 없습니다.")
    private final double gbp;

    @NotNull(message = "JPY 금액을 입력해주세요.")
    @DecimalMin(value = "0", message = "JPY 금액이 0원보다 작을 수 없습니다.")
    @DecimalMax(value = "100000", message = "JPY 금액이 10만원보다 클 수 없습니다.")
    private final double jpy;

    @NotNull(message = "CNY 금액을 입력해주세요.")
    @DecimalMin(value = "0", message = "CNY 금액이 0원보다 작을 수 없습니다.")
    @DecimalMax(value = "100000", message = "CNY 금액이 10만원보다 클 수 없습니다.")
    private final double cny;

    @NotNull(message = "CHF 금액을 입력해주세요.")
    @DecimalMin(value = "0", message = "CHF 금액이 0원보다 작을 수 없습니다.")
    @DecimalMax(value = "100000", message = "CHF 금액이 10만원보다 클 수 없습니다.")
    private final double chf;

    @NotNull(message = "SGD 금액을 입력해주세요.")
    @DecimalMin(value = "0", message = "SGD 금액이 0원보다 작을 수 없습니다.")
    @DecimalMax(value = "100000", message = "SGD 금액이 10만원보다 클 수 없습니다.")
    private final double sgd;

    @NotNull(message = "THB 금액을 입력해주세요.")
    @DecimalMin(value = "0", message = "THB 금액이 0원보다 작을 수 없습니다.")
    @DecimalMax(value = "100000", message = "THB 금액이 10만원보다 클 수 없습니다.")
    private final double thb;

    @NotNull(message = "HKD 금액을 입력해주세요.")
    @DecimalMin(value = "0", message = "HKD 금액이 0원보다 작을 수 없습니다.")
    @DecimalMax(value = "100000", message = "HKD 금액이 10만원보다 클 수 없습니다.")
    private final double hkd;

    @NotNull(message = "KRW 금액을 입력해주세요.")
    @DecimalMin(value = "0", message = "KRW 금액이 0원보다 작을 수 없습니다.")
    @DecimalMax(value = "100000", message = "KRW 금액이 10만원보다 클 수 없습니다.")
    private final double krw;
}
