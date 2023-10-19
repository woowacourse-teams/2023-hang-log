package hanglog.global.config.datasource;

import static hanglog.global.config.datasource.DataSourceType.REPLICA;
import static hanglog.global.config.datasource.DataSourceType.SOURCE;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        final String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        final boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        if (isReadOnly) {
            log.info(currentTransactionName + " Transaction:" + "Replica 서버로 요청합니다.");
            return REPLICA;
        }

        log.info(currentTransactionName + " Transaction:" + "Source 서버로 요청합니다.");
        return SOURCE;
    }
}
