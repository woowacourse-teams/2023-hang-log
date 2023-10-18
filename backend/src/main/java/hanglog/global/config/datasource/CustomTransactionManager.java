package hanglog.global.config.datasource;

import static org.springframework.transaction.TransactionDefinition.ISOLATION_DEFAULT;

import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@Slf4j
public class CustomTransactionManager extends DataSourceTransactionManager {

    public CustomTransactionManager(final DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void doBegin(final Object transaction, final TransactionDefinition definition) {
        final String definitionName = definition.getName();
        if (definitionName != null && definitionName.contains("package_substring")) {
            final int isolationLevel = definition.getIsolationLevel();
            TransactionSynchronizationManager.setCurrentTransactionIsolationLevel(getCustomIsolationLevel(isolationLevel));
            TransactionSynchronizationManager.setCurrentTransactionReadOnly(definition.isReadOnly());
            TransactionSynchronizationManager.setCurrentTransactionName(definition.getName());
        }
        super.doBegin(transaction, definition);
    }

    private Integer getCustomIsolationLevel(final int isolationLevel) {
        if (isolationLevel != ISOLATION_DEFAULT) {
            return isolationLevel;
        }
        return null;
    }
}
