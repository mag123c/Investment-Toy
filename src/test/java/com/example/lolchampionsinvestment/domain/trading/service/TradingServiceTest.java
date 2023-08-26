package com.example.lolchampionsinvestment.domain.trading.service;

import com.example.lolchampionsinvestment.domain.champion.dao.ChampionRepository;
import com.example.lolchampionsinvestment.domain.champion.domain.Champion;
import com.example.lolchampionsinvestment.domain.champion.service.ChampionDataParsingService;
import com.example.lolchampionsinvestment.domain.member.dao.MemberRepository;
import com.example.lolchampionsinvestment.domain.member.domain.Member;
import com.example.lolchampionsinvestment.domain.trading.dao.MemberChampionRepository;
import com.example.lolchampionsinvestment.domain.trading.dao.MemberTradingLogRepository;
import com.example.lolchampionsinvestment.domain.trading.domain.MemberChampion;
import com.example.lolchampionsinvestment.domain.trading.domain.MemberTradingLog;
import com.example.lolchampionsinvestment.domain.trading.domain.TradingType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static com.example.lolchampionsinvestment.domain.member.domain.ME
import static com.example.lolchampionsinvestment.domain.trading.domain.QMemberChampion.memberChampion;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TradingServiceTest {

    @Autowired
    MemberChampionRepository memberChampionRepository;
    @Autowired
    MemberTradingLogRepository memberTradingLogRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ChampionRepository championRepository;
    @Autowired
    ChampionDataParsingService championDataParsingService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JPAQueryFactory jpaQueryFactory;
    @Autowired
    EntityManager em;

    private static String userId;
    private static Member newMember;

    @BeforeEach
    void beforeEach() {
        Member member = memberBuild("test", "test", "test", 100000000, now());
        memberRepository.save(member);

        this.userId = member.getUserId();

        championDataParsingService.championsInsertTable();
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

    /**
     * Buy Logic
     *  1. Check MemberChampion Schema
     *   1-1. If Optional<MemberChampion> is empty : build raw
     *   1-2. else get raw
     *  2-1. Insert Buy Data in MemberChampion
     *  2-2. Update Buy Data in MemberChampion
     *  3. Insert new raw in MemberTradingLog
     *  4. Update raw in Member with current Cash
     */
    @DisplayName("가격이 가장 저렴한 챔피언과 가장 비싼 챔피언을 10개씩 구매한다." +
            "이 떄, 해당 유저가 가진 돈이 모자란 경우는 고려하지 않게 초기 금액을 1억 원으로 설정한다.")
    @Test
    void championBuyTest() {
        //given
        Champion lowestChampion = championRepository.findByPriceInAsc();
        Champion highestChampion = championRepository.findByPriceInDesc();

        Optional<Member> optionalMember = memberRepository.findByUserId(userId);
        int currentCash = 0;
        if(optionalMember.isPresent()) {
            newMember = optionalMember.get();
            currentCash = newMember.getCash();
        }
        //when
        buy(lowestChampion, 10);
        buy(highestChampion, 10);

        int afterCash = jpaQueryFactory
                .select(member.cash)
                .from(member)
                .where(member.id.eq(newMember.getId()))
                .fetchOne();

        //then
        assertThat(currentCash).isNotEqualTo(afterCash);
        assertThat(currentCash).as("구매 금액만큼 차감")
                .isEqualTo(afterCash
                        + lowestChampion.getPrice() * 10
                        + highestChampion.getPrice() * 10);
    }

    private void buy(Champion champion, int amount) {
        int memberId = (int) newMember.getId();
        int championId = Math.toIntExact(champion.getId());

        //1. Check MemberChampion Schema
        Optional<MemberChampion> getCurrentMemberChampionData = memberChampionRepository
                .findCurrentMemberData(memberId, championId);

        //1-1. 2-1. Optional is empty
        if(getCurrentMemberChampionData.isEmpty()) {
            memberChampionRepository.save(memberChampionBuild(memberId, championId, amount));
        }

        //1-2. 2-2. Optional is present
        else {
            MemberChampion memberChampion = getCurrentMemberChampionData.get();
            memberChampionUpdate(memberChampion.getMember_id(),
                    memberChampion.getChampion_id(), memberChampion.getAmount() + amount);
        }

        //3. Insert new raw in MemberTradingLog
        MemberTradingLog memberTradingLog = memberTradingLogBuild(championId, memberId, amount);

        memberTradingLogRepository.save(memberTradingLog);
        //4. Update raw in Member with current Cash
        int totalBuyPrice = champion.getPrice() * amount;
        memberUpdate(totalBuyPrice);

    }

    private MemberChampion memberChampionBuild(int memberId, int championId, int amount) {
        return MemberChampion.builder()
                .member_id(memberId)
                .champion_id(championId)
                .amount(amount)
                .build();
    }

    private MemberTradingLog memberTradingLogBuild(int championId, int memberId, int amount) {
        return MemberTradingLog.builder()
                .user_id(memberId)
                .champion_id(championId)
                .amount(amount)
                .type(TradingType.BUY)
                .create_date(now())
                .build();
    }


    private void memberChampionUpdate(int memberId, int championId, int amount) {
        jpaQueryFactory.update(memberChampion)
                .set(memberChampion.amount, amount)
                .where(memberChampion.member_id.eq(memberId)
                        .and(memberChampion.champion_id.eq(championId)))
                .execute();

        em.flush();
        em.clear();
    }

    private void memberUpdate(int totalBuyPrice) {

        jpaQueryFactory.update(member)
                .set(member.cash, member.cash.subtract(totalBuyPrice))
                .where(member.id.eq(newMember.getId()))
                .execute();

        em.flush();
        em.clear();

    }

}
