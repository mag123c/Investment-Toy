package com.example.lolchampionsinvestment.domain.trading.dao;

import com.example.lolchampionsinvestment.domain.champion.domain.QChampion;
import com.example.lolchampionsinvestment.domain.champion.domain.QChampionPriceLog;
import com.example.lolchampionsinvestment.domain.champion.dto.ChampionPriceDto;
import com.example.lolchampionsinvestment.domain.member.domain.Member;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.lolchampionsinvestment.domain.champion.domain.QChampion.champion;
import static com.example.lolchampionsinvestment.domain.member.domain.QMember.member;
import static com.example.lolchampionsinvestment.domain.trading.domain.QMemberChampion.memberChampion;

@Repository
@RequiredArgsConstructor
public class MemberChampionCustomDao {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    public void memberChampionUpdate(int memberId, int championId, int amount) {
        jpaQueryFactory.update(memberChampion)
                .set(memberChampion.amount, amount)
                .where(memberChampion.member_id.eq(memberId)
                        .and(memberChampion.champion_id.eq(championId)))
                .execute();

        em.flush();
        em.clear();
    }

    public void memberUpdate(Member reqMember, int totalBuyPrice) {

        jpaQueryFactory.update(member)
                .set(member.cash, member.cash.subtract(totalBuyPrice))
                .where(member.id.eq(reqMember.getId()))
                .execute();

        em.flush();
        em.clear();

    }

}
