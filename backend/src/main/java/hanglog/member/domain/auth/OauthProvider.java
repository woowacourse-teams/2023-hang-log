package hanglog.member.domain.auth;

import org.springframework.web.client.RestTemplate;

public interface OauthProvider {

    RestTemplate restTemplate = new RestTemplate();

    boolean is(String name);
    UserInfo getUserInfo(String code);
}
