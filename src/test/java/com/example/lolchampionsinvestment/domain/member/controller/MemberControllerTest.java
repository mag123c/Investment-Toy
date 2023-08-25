package com.example.lolchampionsinvestment.domain.member.controller;

import com.example.lolchampionsinvestment.config.SecurityConfig;
import com.example.lolchampionsinvestment.domain.member.domain.Member;
import com.example.lolchampionsinvestment.domain.member.dto.MemberSigninDto;
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

/**
 * @issue
 * 1. mockMvc Bean Null
 *  - service Dependency Injection is failed maybe
 *  - method test will finish when issue solved
 */
@WebMvcTest(controllers = MemberController.class)
@ActiveProfiles("test")
@Import(SecurityConfig.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    private MemberService memberService;

    private Member memberBuild(String userId, String pw, String nickname, int cash, LocalDateTime create_date, LocalDateTime update_date) {
        return Member.builder()
                .userId(userId)
                .pw(passwordEncoder.encode(pw))
                .nickname(nickname)
                .cash(cash)
                .create_date(create_date)
                .update_date(update_date)
                .build();
    }

    private MemberSigninDto memberSignInDtoBuild(String userId, String pw) {
        return MemberSigninDto.builder()
                .userId(userId)
                .pw(pw)
                .build();
    }

    @DisplayName("회원가입 성공")
    @Test
    void signUpSuccess() throws Exception {
        //given
        Member member = memberBuild("test", "test1234", "테스트2", 0, now(), null);

        //when //then
        mockMvc.perform(
                        post("/api/v1/member/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(member))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("로그인 성공")
    @Test
    void signInSuccess() throws Exception {
        //given
        MemberSigninDto memberSigninDto = memberSignInDtoBuild("test", "test1234");

        //when //then
        mockMvc.perform(
                        post("/api/v1/member/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(memberSigninDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

}