package com.example.lolchampionsinvestment.domain.member.service;

import com.example.lolchampionsinvestment.domain.member.dao.MemberRepository;
import com.example.lolchampionsinvestment.domain.member.domain.Member;
import com.example.lolchampionsinvestment.domain.member.dto.MemberSigninDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * signupCheck
     * 0 : SignUp
     * -1 : ID duplicate
     * -2 : NickName Duplicate
     * -3 : ID Length Error
     * -4 : NickName Length Error
     * -5 : DataIntegrityViolationException
     */
    public int signUp(Member member) {
        //ID, NickName 중복 확인
        int signUpCheck = signUpCheck(member);
        if(signUpCheck != 0) return signUpCheck;

        try {
            member.setPw(passwordEncoder.encode(member.getPw()));
            memberRepository.save(member);
            return 0;
        } catch (DataIntegrityViolationException e) {
            return -5;
        }
    }

    public int signUpCheck(Member member) {
        if(memberRepository.findByUserId(member.getUserId()).isPresent()) return -1;
        if(memberRepository.findByNickname(member.getNickname()).isPresent()) return -2;

        int idLength = member.getUserId().length();
        int nickNameLength = member.getNickname().length();

        if(idLength < 4 || idLength > 20) return -3;
        if(nickNameLength < 4 || nickNameLength > 20) return -4;

        return 0;
    }

    /**
     * @return
     * 0 : 로그인 성공
     * -1 : 입력한 ID와 일치하는 계정이 존재하지 않음
     * -2 : 비밀번호 불일치
     */
    public int signIn(MemberSigninDto memberSigninDto) {
        Optional<Member> member = memberRepository.findByUserId(memberSigninDto.getUserId());
        if(member.isEmpty()) return -1;

        String encodePW = member.get().getPw();
        String rawPW = memberSigninDto.getPw();
        if(passwordEncoder.matches(rawPW, encodePW)) return 0;
        else return -2;
    }

}
