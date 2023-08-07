package hanglog.member.domain.auth;

import org.springframework.web.client.RestTemplate;

public interface Provider {

    RestTemplate restTemplate = new RestTemplate();

    boolean is(String name);
    String getAccessToken(String code);
    UserInfo getUserInfo(String accessToken);
}
