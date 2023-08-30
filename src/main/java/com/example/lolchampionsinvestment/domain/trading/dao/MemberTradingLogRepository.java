package com.example.lolchampionsinvestment.domain.trading.dao;

import com.example.lolchampionsinvestment.domain.trading.domain.MemberTradingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberTradingLogRepository extends JpaRepository<MemberTradingLog, Long> {
}
