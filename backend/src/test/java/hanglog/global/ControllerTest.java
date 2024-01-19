package hanglog.global;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import hanglog.admin.AdminLoginArgumentResolver;
import hanglog.global.restdocs.RestDocsConfiguration;
import hanglog.login.LoginArgumentResolver;
import hanglog.login.domain.repository.RefreshTokenRepository;
import hanglog.login.infrastructure.BearerAuthorizationExtractor;
import hanglog.login.infrastructure.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@Import(RestDocsConfiguration.class)
@ExtendWith(RestDocumentationExtension.class)
public abstract class ControllerTest {

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected LoginArgumentResolver loginArgumentResolver;

    @Autowired
    protected AdminLoginArgumentResolver adminLoginArgumentResolver;

    @MockBean
    protected JwtProvider jwtProvider;

    @MockBean
    protected RefreshTokenRepository refreshTokenRepository;

    @MockBean
    BearerAuthorizationExtractor bearerExtractor;


    @BeforeEach
    void setUp(
            final WebApplicationContext context,
            final RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(restDocs)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }
}
