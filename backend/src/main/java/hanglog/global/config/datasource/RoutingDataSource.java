package hanglog.global.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        final String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        final boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        logger.info(currentTransactionName + " Transaction Read Only가 " + isReadOnly + " 입니다.");

        if (isReadOnly) {
            logger.info("Replica 서버로 요청합니다.");
            return "replica";
        }

        logger.info("Source 서버로 요청합니다.");
        return "source";
    }
}
