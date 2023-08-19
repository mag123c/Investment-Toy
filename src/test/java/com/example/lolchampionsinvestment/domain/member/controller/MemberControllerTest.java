package com.example.lolchampionsinvestment.domain.member.controller;

import com.example.lolchampionsinvestment.config.SecurityConfig;
import com.example.lolchampionsinvestment.domain.champion.controller.ChampionDataParsingController;
import com.example.lolchampionsinvestment.domain.member.domain.Member;
import com.example.lolchampionsinvestment.domain.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class)
@ActiveProfiles("test")
@Import(SecurityConfig.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;
//    @Autowired
//    MemberRepository memberRepository;
//
//    @BeforeEach
//    void before() {
//        Member memmber = memberBuild("test", "test1234", "테스트2", 0, now());
//        memberRepository.save(memmber);
//    }
    private Member memberBuild(String userId, String pw, String nickname, int cash, LocalDateTime create_date) {
        return Member.builder()
                .userId(userId)
                .pw(passwordEncoder.encode(pw))
                .nickname(nickname)
                .cash(cash)
                .create_date(create_date)
                .build();
    }

    @DisplayName("회원가입에 성공했습니다.")
    @Test
    void signUpTest() throws Exception {
        //when //then
        mockMvc.perform(
                        post("/api/v1/member/new")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));

        verify(memberService).singUp(memberBuild("test", "test1234", "테스트2", 0, now()));
    }

}