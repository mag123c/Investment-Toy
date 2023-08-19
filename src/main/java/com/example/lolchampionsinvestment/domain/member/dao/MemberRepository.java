package com.example.lolchampionsinvestment.domain.member.dao;

import com.example.lolchampionsinvestment.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String userId);

    Optional<Member> findByNickname(String nickname);
}
