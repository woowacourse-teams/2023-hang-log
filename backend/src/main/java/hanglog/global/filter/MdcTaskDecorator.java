package hanglog.global.filter;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class MdcTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(final Runnable runnable) {
        final Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        Map<String, String> map = MDC.getCopyOfContextMap();
        for (String key : map.keySet()) {
            System.out.println("decorate : " + map.get(key));
        }
        return () -> {
            MDC.setContextMap(copyOfContextMap);
            runnable.run();
        };
    }
}
