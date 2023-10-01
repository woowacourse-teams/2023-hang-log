package hanglog.image.presentation;

import static hanglog.global.restdocs.RestDocsConfiguration.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hanglog.global.ControllerTest;
import hanglog.image.dto.ImagesResponse;
import hanglog.image.service.ImageService;
import java.io.FileInputStream;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;


@WebMvcTest(ImageController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class ImageControllerTest extends ControllerTest {

    @MockBean
    private ImageService imageService;

    @DisplayName("여러 장의 사진을 업로드할 수 있다.")
    @Test
    void uploadImage() throws Exception {
        // given
        final MockMultipartFile file = new MockMultipartFile("images",
                "static/images/logo.png",
                "image/png",
                new FileInputStream("./src/test/resources/static/images/logo.png"));

        final ImagesResponse imagesResponse = new ImagesResponse(List.of(""));

        when(imageService.saveImages(any())).thenReturn(imagesResponse);

        // when & then
        mockMvc.perform(
                        multipart(POST, "/images")
                                .file(file))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(restDocs.document(
                        requestParts(
                                partWithName("images").description("이미지 파일, 최대 5개, 개당 최대 10MB")
                        ),
                        responseFields(
                                fieldWithPath("imageUrls")
                                        .type(JsonFieldType.ARRAY)
                                        .description("저장된 이미지 url 배열")
                                        .attributes(field("constraint", "nginx 주소 + 해싱된 이름"))
                        )
                ));
    }
}
