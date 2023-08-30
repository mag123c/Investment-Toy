package com.example.lolchampionsinvestment.domain.trading.service;

import com.example.lolchampionsinvestment.domain.champion.dao.ChampionRepository;
import com.example.lolchampionsinvestment.domain.champion.domain.Champion;
import com.example.lolchampionsinvestment.domain.member.dao.MemberRepository;
import com.example.lolchampionsinvestment.domain.member.domain.Member;
import com.example.lolchampionsinvestment.domain.trading.dao.MemberChampionCustomDao;
import com.example.lolchampionsinvestment.domain.trading.dao.MemberChampionRepository;
import com.example.lolchampionsinvestment.domain.trading.dao.MemberTradingLogRepository;
import com.example.lolchampionsinvestment.domain.trading.domain.MemberChampion;
import com.example.lolchampionsinvestment.domain.trading.domain.MemberTradingLog;
import com.example.lolchampionsinvestment.domain.trading.domain.TradingType;
import com.example.lolchampionsinvestment.domain.trading.dto.ChampionTradingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class TradingService {

    private final MemberChampionRepository memberChampionRepository;
    private final MemberTradingLogRepository memberTradingLogRepository;
    private final MemberRepository memberRepository;
    private final ChampionRepository championRepository;
    private final MemberChampionCustomDao memberChampionCustomDao;

    /**
     * Buy Logic
     *  0. Check exist raw that param with schema
     *  1. Check MemberChampion Schema
     *   1-1. If Optional<MemberChampion> is empty : build raw
     *   1-2. else get raw
     *  2-1. Insert Buy Data in MemberChampion
     *  2-2. Update Buy Data in MemberChampion
     *  3. Insert new raw in MemberTradingLog
     *  4. Update raw in Member with current Cash
     *
     * @return
     *   0 : 정상 수행
     * -10 : 알 수 없는 오류 발생 (member data is empty)
     * -20 : 알 수 없는 오류 발생 (champion data is empty)
     */
    public int championBuy(ChampionTradingDto championTradingDto, String userId) {

        //0. Check exist raw that param with schema
        Optional<Member> memberData = memberRepository.findByUserId(userId);
        Optional<Champion> championData = championRepository.findByName(championTradingDto.getName());
        if(memberData.isEmpty()) return -10;
        if(championData.isEmpty()) return -20;

        Member reqMember = memberData.get();
        Champion targetChampion = championData.get();

        int memberId = (int) reqMember.getId();
        int championId = Math.toIntExact(targetChampion.getId());
        int amount = championTradingDto.getAmount();

        //1. Check MemberChampion Schema
        Optional<MemberChampion> memberChampionData = memberChampionRepository
                .findCurrentMemberData(memberId, championId);

        //1-1. 2-1. Optional is empty
        if(memberChampionData.isEmpty()) {
            memberChampionRepository.save(memberChampionBuild(memberId, championId, amount));
        }

        //1-2. 2-2. Already exists raw
        else {
            MemberChampion memberChampion = memberChampionData.get();
            memberChampionCustomDao.memberChampionUpdate(memberChampion.getMember_id(),
                    memberChampion.getChampion_id(), memberChampion.getAmount() + amount);
        }

        //3. Insert new raw in MemberTradingLog
        MemberTradingLog memberTradingLog = memberTradingLogBuild(championId, memberId, amount);

        memberTradingLogRepository.save(memberTradingLog);
        //4. Update raw in Member with current Cash
        int totalBuyPrice = targetChampion.getPrice() * amount;
        memberChampionCustomDao.memberUpdate(reqMember, totalBuyPrice);

        return 0;

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


}
