package com.withsports.userservice.domain.user.web;


import com.withsport.userservice.domain.user.web.UserController;
import com.withsport.userservice.global.security.SecurityConfig;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = UserController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestConfig.class)
@AutoConfigureRestDocs(uriHost = "127.0.0.1", uriPort = 8001)
public class UserApiTest {
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @MockBean
//    UserService userService;
//
//    @SpyBean
//    CookieProvider cookieProvider;
//
//
//    @Test
//    @DisplayName("로그인된 회원 조회")
//    void getUserByToken() throws Exception{
//        long userId = 1L;
//
//        UserDto willReturnDto = UserDto.builder()
//                .id(1L)
//                .email("jbin3031@gachon.ac.kr")
//                .password("password!@#")
//                .name("박정빈")
//                .nickname("두두지")
//                .address("SEOUL")
//                .build();
//
//        given(userService.findUserByUserId(userId)).willReturn(willReturnDto);
//
//        ResultActions actions = mockMvc.perform(get("/user")
//                .header("user-id", userId));
//
//        actions.andExpect(status().isOk())
//                .andExpect(jsonPath("code").value(Code.SUCCESS.name()))
//                .andExpect(jsonPath("message").value(""))
//                .andExpect(jsonPath("data.userId").value(1))
//                .andExpect(jsonPath("data.email").value("jbin3031@gachon.ac.kr"))
//                .andExpect(jsonPath("data.name").value("박정빈"))
//                .andExpect(jsonPath("data.nickname").value("두두지"))
//                .andExpect(jsonPath("data.address").value("SEOUL"))
//                .andDo(print())
//                .andDo(document("customer-get-mypage",
//                        requestHeaders(
//                                headerWithName("user-id").description("로그인한 유저 id")
//                        ),
//                        responseFields(
//                                fieldWithPath("code").description("결과코드 SUCCESS/ERROR"),
//                                fieldWithPath("message").description("메시지"),
//                                fieldWithPath("data.userId").description("회원 고유번호"),
//                                fieldWithPath("data.userName").description("회원 이름"),
//                                fieldWithPath("data.email").description("회원 이메일"),
//                                fieldWithPath("data.phoneNumber").description("회원 휴대폰 번호")
//                        ))
//                );
//
//    }

}
