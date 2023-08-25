package com.example.lolchampionsinvestment.domain.trading.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "MemberTradingLog")
@Getter
@NoArgsConstructor
public class MemberTradingLog {
    //index maybe user_id, (user_id, champion_id)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private long id;

    @Column(name = "user_id")
    private int user_id;

    //champion price get with join champion schema
    @Column(name = "champion_id")
    private int champion_id;

    @Column(name = "amount")
    private int amount;

    @Column(name = "type")
    private TradingType type;

    @Column(name = "create_date")
    private LocalDateTime create_date;

    @Builder
    public MemberTradingLog(int user_id, int champion_id, int amount, TradingType type, LocalDateTime create_date) {
        this.user_id = user_id;
        this.champion_id = champion_id;
        this.amount = amount;
        this.type = type;
        this.create_date = create_date;
    }
}
