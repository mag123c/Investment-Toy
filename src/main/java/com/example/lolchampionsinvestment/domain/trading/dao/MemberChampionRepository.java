package com.example.lolchampionsinvestment.domain.trading.dao;

import com.example.lolchampionsinvestment.domain.trading.domain.MemberChampion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberChampionRepository extends JpaRepository<MemberChampion, Long> {

    @Query("select mc from MemberChampion mc where mc.member_id = :member_id and mc.champion_id = :champion_id")
    Optional<MemberChampion> findCurrentMemberData(
            @Param("member_id") int member_id, @Param("champion_id") int champion_id
    );
}
