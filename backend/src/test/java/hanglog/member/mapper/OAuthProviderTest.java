package hanglog.member.mapper;

import static hanglog.member.mapper.fixture.MemberFixture.GOOGLE_USER_INFO_JSON_STRING;
import static hanglog.member.mapper.fixture.MemberFixture.KAKAO_USER_INFO_JSON_STRING;
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
            final String jsonString
    ) throws JsonProcessingException {
        // given
        final JsonNode userResourceNode = new ObjectMapper().readTree(jsonString);

        // when
        final OAuthProvider oAuthProvider = OAuthProvider.mappingProvider(userResourceNode,registrationId);

        // then
       assertSoftly(softly->{
           softly.assertThat(oAuthProvider.getSocialLoginId()).isEqualTo(registrationId + "_id");
           softly.assertThat(oAuthProvider.getNickname()).isEqualTo(registrationId + "_test");
           softly.assertThat(oAuthProvider.getPicture()).isEqualTo(registrationId + "_image_url");
       });
    }

    private static Stream<Arguments> oAuthRegisterProvider(){
        return Stream.of(
                Arguments.of("google", GOOGLE_USER_INFO_JSON_STRING),
                Arguments.of("kakao", KAKAO_USER_INFO_JSON_STRING)
        );
    }
}
