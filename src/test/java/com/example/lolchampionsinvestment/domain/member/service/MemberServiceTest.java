package com.example.lolchampionsinvestment.domain.member.service;

import com.example.lolchampionsinvestment.domain.member.dao.MemberRepository;
import com.example.lolchampionsinvestment.domain.member.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void before() {
        Member memmber = memberBuild("test", "test1234", "테스트2", 0, now());
        memberRepository.save(memmber);
    }

    @AfterEach
    void after() {
        memberRepository.deleteAllInBatch();
    }

    private Member memberBuild(String userId, String pw, String nickname, int cash, LocalDateTime create_date) {
        return Member.builder()
                .userId(userId)
                .pw(passwordEncoder.encode(pw))
                .nickname(nickname)
                .cash(cash)
                .create_date(create_date)
                .build();
    }

    public int signUpChkWithDuplicate(Member member) {
        if(memberRepository.findByUserId(member.getUserId()).isPresent()) return -1;
        if(memberRepository.findByNickname(member.getNickname()).isPresent()) return -2;
        else return 0;
    }

    public int signUpChkWithLength(Member member) {
        int idLength = member.getUserId().length();
        int nickNameLength = member.getNickname().length();

        if(idLength < 4 || idLength > 20) return -1;
        if(nickNameLength < 4 || nickNameLength > 20) return -2;

        try {
            memberRepository.save(member);
            return 1;
        } catch (DataIntegrityViolationException e) {
            return -3;
        }
    }

    @DisplayName("중복된 ID 혹은 닉네임은 회원가입이 불가능하다")
    @Test
    void signUpChkWhenIDorNickNameDuplicate(){
        //given
        Member member1 = memberBuild("test", "test1234", "테스트1", 0, now());
        Member member2 = memberBuild("test2", "test1234", "테스트2", 0, now());
        //when
        int member1Check = signUpChkWithDuplicate(member1);
        int member2Check = signUpChkWithDuplicate(member2);

        //then
        assertThat(member1Check).as("ID 중복").isEqualTo(-1);
        assertThat(member2Check).as("NICKNAME 중복").isEqualTo(-2);
    }

    @DisplayName("중복 여부는 통과했으나, 아이디와 닉네임 중 4자 이상 20자 이하의 조건을 만족하지 못하면 회원가입이 불가능하다.")
    @Test
    void signUpChkWhenIDorNickNameLength(){
        //given
        Member member1 = memberBuild("testtesttesttesttesttest", "test1234", "테스트1", 0, now());
        Member member2 = memberBuild("test2", "test1234", "테스트테스트테스트테스트테스트테스트테스트", 0, now());

        int member1DuplicateCheck = signUpChkWithDuplicate(member1);
        int member2DuplicateCheck = signUpChkWithDuplicate(member2);
        //when
        int member1LengthCheck = 0;
        int member2LengthCheck = 0;
        if(member1DuplicateCheck == 0) member1LengthCheck = signUpChkWithLength(member1);
        if(member2DuplicateCheck == 0) member2LengthCheck = signUpChkWithLength(member2);


        //then
        assertThat(member1LengthCheck)
                .as("ID는 4자 이상 20자 이하여야 합니다. 현재 입력하신 닉네임은 '" + member1.getUserId() + "' 입니다.")
                .isEqualTo(-1);
        assertThat(member2LengthCheck)
                .as("NICKNAME은 4자 이상 20자 이하여야 합니다. 현재 입력하신 닉네임은 '" + member2.getNickname() + "' 입니다.")
                .isEqualTo(-2);
    }
}