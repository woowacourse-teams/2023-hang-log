package hanglog.global.detector;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

@RequiredArgsConstructor
public class ConnectionProxyHandler implements MethodInterceptor {

    private static final String JDBC_PREPARE_STATEMENT_METHOD_NAME = "prepareStatement";
    private static final String HIKARI_CONNECTION_NAME = "HikariProxyConnection";

    private final Object connection;
    private final LoggingForm loggingForm;

    @Nullable
    @Override
    public Object invoke(@Nonnull final MethodInvocation invocation) throws Throwable {
        final Object result = invocation.proceed();

        if (hasConnection(result) && hasPreparedStatementInvoked(invocation)) {
            final ProxyFactory proxyFactory = new ProxyFactory(result);
            proxyFactory.addAdvice(new PreparedStatementProxyHandler(loggingForm));
            return proxyFactory.getProxy();
        }

        return result;
    }

    private boolean hasPreparedStatementInvoked(final MethodInvocation invocation) {
        final Object targetObject = invocation.getThis();
        if (targetObject == null) {
            return false;
        }
        final Class<?> targetClass = targetObject.getClass();
        return targetClass.getName().contains(HIKARI_CONNECTION_NAME) &&
                invocation.getMethod().getName().equals(JDBC_PREPARE_STATEMENT_METHOD_NAME);
    }

    private boolean hasConnection(final Object result) {
        return result != null;
    }

    public Object getProxy() {
        final ProxyFactory proxyFactory = new ProxyFactory(connection);
        proxyFactory.addAdvice(this);
        return proxyFactory.getProxy();
    }
}
