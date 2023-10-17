package hanglog.oauth.domain.oauthprovider;

import hanglog.oauth.domain.oauthuserinfo.OauthUserInfo;
import org.springframework.web.client.RestTemplate;

public interface OauthProvider {

    RestTemplate restTemplate = new RestTemplate();

    boolean is(String name);
    OauthUserInfo getUserInfo(String code);
}
