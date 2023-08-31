package hanglog.image.util;

import static hanglog.global.exception.ExceptionCode.INVALID_IMAGE_URL;

import hanglog.global.exception.BadRequestException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageUrlConverter {

    private static final String EMPTY_STRING = "";
    private static final int URL_INDEX = 1;
    private static final int VALID_PARSED_URL_SIZE = 2;
    private static final int EMPTY_STRING_INDEX = 0;

    private static String imageBaseUrl = "https://hanglog.com/img/";

    @Value("${image.base-url}")
    public void setImageBaseUrl(final String value) {
        imageBaseUrl = value;
    }

    public static String convertNameToUrl(final String name) {
        return imageBaseUrl + name;
    }

    public static String convertUrlToName(final String url) {
        final List<String> parsedUrl = List.of(url.split(imageBaseUrl));
        validateImageUrlFormat(parsedUrl);
        return parsedUrl.get(URL_INDEX);
    }

    private static void validateImageUrlFormat(final List<String> parsedUrl) {
        if (parsedUrl.size() != VALID_PARSED_URL_SIZE) {
            throw new BadRequestException(INVALID_IMAGE_URL);
        }
        if (!parsedUrl.get(EMPTY_STRING_INDEX).equals(EMPTY_STRING)) {
            throw new BadRequestException(INVALID_IMAGE_URL);
        }
    }
}
