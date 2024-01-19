package hanglog.admin.domain.type;

import static hanglog.global.exception.ExceptionCode.NULL_ADMIN_AUTHORITY;

import hanglog.global.exception.AdminException;
import java.util.Arrays;

public enum AdminType {
    ADMIN, MASTER;

    public static AdminType getMappedAdminType(final String adminType) {
        return Arrays.stream(values())
                .filter(value -> value.name().toUpperCase().equals(adminType))
                .findAny()
                .orElseThrow(() -> new AdminException(NULL_ADMIN_AUTHORITY));
    }
}
