package hanglog.global;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageNameParser {

    public static String parse(final String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
