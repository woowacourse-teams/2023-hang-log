package hanglog.image.util;

import static hanglog.global.exception.ExceptionCode.INVALID_IMAGE_URL;

import hanglog.global.exception.BadRequestException;
import java.util.List;

public class ImageNameUrlConverter {
    private static final String IMAGE_BASE_URL = "https://hanglog.com/img/";
    private static final String EMPTY_STRING = "";
    private static final int URL_INDEX = 1;
    private static final int VALID_PARSED_URL_SIZE = 2;
    private static final int EMPTY_STRING_INDEX = 0;

    public static String convertUrlToName(final String url) {
        validateContainBaseUrl(url);

        final List<String> splitUrls = List.of(url.split(IMAGE_BASE_URL));
        validateParsedUrl(splitUrls);

        return splitUrls.get(URL_INDEX);
    }

    public static String convertNameToUrl(final String name) {
        return IMAGE_BASE_URL + name;
    }

    private static void validateContainBaseUrl(final String url) {
        if (!url.contains(IMAGE_BASE_URL)) {
            throw new BadRequestException(INVALID_IMAGE_URL);
        }
    }

    private static void validateParsedUrl(final List<String> parsedUrl) {
        if (parsedUrl.size() != VALID_PARSED_URL_SIZE) {
            throw new BadRequestException(INVALID_IMAGE_URL);
        }
        if (!parsedUrl.get(EMPTY_STRING_INDEX).equals(EMPTY_STRING)) {
            throw new BadRequestException(INVALID_IMAGE_URL);
        }
    }
}
