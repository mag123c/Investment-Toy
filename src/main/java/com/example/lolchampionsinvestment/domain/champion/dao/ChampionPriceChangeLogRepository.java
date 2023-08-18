package com.example.lolchampionsinvestment.domain.champion.dao;

import com.example.lolchampionsinvestment.domain.champion.domain.ChampionPriceChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChampionPriceChangeLogRepository extends JpaRepository<ChampionPriceChangeLog, Long> {

}
