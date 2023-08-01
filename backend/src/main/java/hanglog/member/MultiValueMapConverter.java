package hanglog.member;

import static hanglog.global.exception.ExceptionCode.FAIL_TO_CONVERT_URL_PARAMETER;
import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.global.exception.AuthException;
import hanglog.member.dto.AccessTokenRequest;
import java.util.Map;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@NoArgsConstructor(access = PRIVATE)
public abstract class MultiValueMapConverter {

    public static MultiValueMap<String, String> convert(final ObjectMapper objectMapper, final AccessTokenRequest dto)
            throws AuthException {
        try {
            final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            final Map<String, String> map = objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {});
            params.setAll(map);
            return params;
        } catch (Exception e) {
            throw new AuthException(FAIL_TO_CONVERT_URL_PARAMETER);
        }
    }
}
