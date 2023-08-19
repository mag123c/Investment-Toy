package com.example.lolchampionsinvestment.domain.member.service;

import com.example.lolchampionsinvestment.domain.member.dao.MemberRepository;
import com.example.lolchampionsinvestment.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public int memberSignUp(Member member) {
        if(memberRepository.findByUserId(member.getUserId()).isPresent()) return -1;
        if(memberRepository.findByNickname(member.getNickname()).isPresent()) return -2;
        else return 0;
    }

}
