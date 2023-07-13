package hanglog.member.mapper;

import static hanglog.feature.MemberFeature.GOOGLE_USER_INFO_JSON_STRING;
import static hanglog.feature.MemberFeature.KAKAO_USER_INFO_JSON_STRING;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


class OAuthProviderTest {
    @ParameterizedTest(name = "{0} OAuth2 요청에서 id, 닉네임, 프로필사진을 가져온다.")
    @MethodSource("oAuthRegisterProvider")
    void mappingProvider(
            final String registrationId,
            final String jsonString,
            final long expectedId,
            final String expectedNickname,
            final String expectedImage
    ) throws JsonProcessingException {

        // given
        final JsonNode userResourceNode = new ObjectMapper().readTree(jsonString);

        // when
        final OAuthProvider oAuthProvider = OAuthProvider.mappingProvider(userResourceNode,registrationId);

        //then
       assertSoftly(softly->{
           softly.assertThat(oAuthProvider.getId()).isEqualTo(expectedId);
           softly.assertThat(oAuthProvider.getNickname()).isEqualTo(expectedNickname);
           softly.assertThat(oAuthProvider.getPicture()).isEqualTo(expectedImage);
       });
    }

    private static Stream<Arguments> oAuthRegisterProvider(){
        return Stream.of(
                Arguments.of("google",
                        GOOGLE_USER_INFO_JSON_STRING,
                        123l,"google_test","google_image-url"
                ),
                Arguments.of("kakao",
                                KAKAO_USER_INFO_JSON_STRING
                                ,
                        123l,"kakao_test","kakao_image-url"
                )
        );
    }
}
