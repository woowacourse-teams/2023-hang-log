package hanglog.global.decorator;

import java.util.Map;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

public class MdcTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(final Runnable runnable) {
        final Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        return () -> {
            MDC.setContextMap(copyOfContextMap);
            runnable.run();
        };
    }
}
