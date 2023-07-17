package hanglog.member.mapper.fixture;

public class MemberFixture {

    public static final String GOOGLE_USER_INFO_JSON_STRING =
            "{\"id\":\"123\","
            + "\"email\":\"test@test.com\","
            + "\"verified_email\":true,"
            + "\"name\":\"google_test\","
            + "\"given_name\":\"google_test\","
            + "\"family_name\":\"google_test\","
            + "\"picture\":\"google_image-url\","
            + "\"locale\":\"ko\"}";
    public static final String KAKAO_USER_INFO_JSON_STRING =
                    "{\"id\":123,"
                    + "\"connected_at\":\"2023-07-13T01:18:52Z\","
                    + "\"properties\":{"
                    + "\"nickname\":\"kakao_test\","
                    + "\"profile_image\":\"kakao_image-url\","
                    + "\"thumbnail_image\":\"test_thumbnail_image\"},"
                    + "\"kakao_account\":{\"profile_nickname_needs_agreement\":false,"
                    + "\"profile_image_needs_agreement\":false,"
                    + "\"profile\":{"
                    + "\"nickname\":\"kakao_test\","
                    + "\"thumbnail_image_url\":\"kakao_image-url\","
                    + "\"is_default_image\":false},"
                    + "\"profile_image_url\":\"test_thumbnail_image\","
                    + "\"has_email\":true,"
                    + "\"email_needs_agreement\":false,"
                    + "\"is_email_valid\":true,"
                    + "\"is_email_verified\":true,"
                    + "\"email\":\"fruturum@nate.com\"}}";
}
