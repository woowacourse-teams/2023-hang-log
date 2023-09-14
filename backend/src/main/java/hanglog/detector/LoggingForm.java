package hanglog.detector;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoggingForm {

    private String apiUrl;
    private String apiMethod;
    private Long queryCounts = 0L;
    private Long queryTime = 0L;

    public void queryCountUp() {
        queryCounts++;
    }

    public void addQueryTime(final Long queryTime) {
        this.queryTime += queryTime;
    }

    public void setApiUrl(final String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public void setApiMethod(final String apiMethod) {
        this.apiMethod = apiMethod;
    }
}
