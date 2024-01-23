package hanglog.exception;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;

public class ExceptionResponseFieldsSnippet extends AbstractFieldsSnippet {

    public ExceptionResponseFieldsSnippet(
            final String type,
            final List<FieldDescriptor> descriptors,
            final boolean ignoreUndocumentedFields
    ) {
        super(type, descriptors, null, ignoreUndocumentedFields);
    }

    @Override
    protected MediaType getContentType(final Operation operation) {
        return operation.getResponse().getHeaders().getContentType();
    }

    @Override
    protected byte[] getContent(final Operation operation) {
        return operation.getResponse().getContent();
    }
}
