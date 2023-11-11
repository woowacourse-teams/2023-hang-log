package hanglog.global;

import static com.slack.api.webhook.WebhookPayloads.payload;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import hanglog.global.filter.MdcKey;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SlackMessageSender {

    @Value("${slack.webhook.url}")
    private String webhookUrl;

    private final Slack slack;

    public SlackMessageSender() {
        slack = Slack.getInstance();
    }

    @Async
    public void send(final Exception exception) throws IOException {
        slack.send(webhookUrl, payload(p -> p.attachments(generateSlackAttachment(exception))));
    }

    private List<Attachment> generateSlackAttachment(final Exception exception) {
        final Attachment requestInfo = Attachment.builder()
                .color("ff0000")
                .title(exception.toString())
                .fields(Arrays.stream(MdcKey.values())
                        .map(key -> generateSlackField(key.name(), MDC.get(key.name())))
                        .toList())
                .build();

        final Attachment stackTrace = Attachment.builder()
                .fields(List.of(generateSlackField("EXCEPTION_STACK_TRACE", getStackTraceSummary(exception))))
                .build();

        return List.of(requestInfo, stackTrace);
    }

    private String getStackTraceSummary(final Exception exception) {
        final StackTraceElement[] stackTrace = exception.getStackTrace();
        final StringBuilder summary = new StringBuilder();
        final int linesToShow = Math.min(stackTrace.length, 5);
        for (int i = 0; i < linesToShow; i++) {
            summary.append(stackTrace[i].toString()).append("\n");
        }
        return summary.toString();
    }

    private Field generateSlackField(final String title, final String value) {
        return Field.builder()
                .title(title)
                .value(value)
                .valueShortEnough(false)
                .build();
    }
}
