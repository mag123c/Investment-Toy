package com.example.lolchampionsinvestment.domain.member.controller;

import com.example.lolchampionsinvestment.api.ApiResponse;
import com.example.lolchampionsinvestment.domain.champion.service.ChampionDataParsingService;
import com.example.lolchampionsinvestment.domain.member.domain.Member;
import com.example.lolchampionsinvestment.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/v1/member/new")
    public ApiResponse<String> signUp(Member member) {
        int signUpCheck = memberService.signUp(member);
        try {
            switch(signUpCheck) {
                case 0: return ApiResponse.ok("회원가입 완료");
                case -1: return ApiResponse.of(HttpStatus.BAD_REQUEST, ("중복된 ID가 존재합니다."), null);
                case -2: return ApiResponse.of(HttpStatus.BAD_REQUEST, ("중복된 닉네임이 존재합니다."), null);
                case -3: return ApiResponse.of(HttpStatus.BAD_REQUEST, ("ID는 4 ~ 20글자로 작성해주세요."), null);
                case -4: return ApiResponse.of(HttpStatus.BAD_REQUEST, ("닉네임은 4 ~ 20글자로 작성해주세요."), null);
                default: return ApiResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류 발생", null);
            }
        } catch (HttpClientErrorException httpClientErrorException) {
            return ApiResponse.of(HttpStatus.BAD_REQUEST, httpClientErrorException.getMessage(), null);
        } catch (Exception e) {
            return ApiResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while parsing and saving champion data", null);
        }
    }

}
