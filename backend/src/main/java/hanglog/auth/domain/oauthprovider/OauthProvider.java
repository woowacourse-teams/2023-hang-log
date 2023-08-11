package hanglog.auth.domain.oauthprovider;

import hanglog.auth.domain.oauthuserinfo.OauthUserInfo;
import org.springframework.web.client.RestTemplate;

public interface OauthProvider {

    RestTemplate restTemplate = new RestTemplate();

    boolean is(String name);
    OauthUserInfo getUserInfo(String code);
}
