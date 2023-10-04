package hanglog.global.detector;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@RequiredArgsConstructor
public class PreparedStatementProxyHandler implements MethodInterceptor {

    private static final List<String> JDBC_QUERY_METHOD = List.of("executeQuery", "execute", "executeUpdate");

    private final LoggingForm loggingForm;

    @Nullable
    @Override
    public Object invoke(@Nonnull final MethodInvocation invocation) throws Throwable {

        final Method method = invocation.getMethod();

        if (JDBC_QUERY_METHOD.contains(method.getName())) {
            final long startTime = System.currentTimeMillis();
            final Object result = invocation.proceed();
            final long endTime = System.currentTimeMillis();

            loggingForm.addQueryTime(endTime - startTime);
            loggingForm.queryCountUp();

            return result;
        }

        return invocation.proceed();
    }
}
