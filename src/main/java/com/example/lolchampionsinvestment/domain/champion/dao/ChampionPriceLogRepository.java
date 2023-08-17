package com.example.lolchampionsinvestment.domain.champion.dao;

import com.example.lolchampionsinvestment.domain.champion.domain.ChampionPriceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionPriceLogRepository extends JpaRepository<ChampionPriceLog, Long> {

    @Query("select c from ChampionPriceLog c where c.champion_id = :champion_id")
    ChampionPriceLog findByChampion_Id(@Param("champion_id") int champion_id);
}
