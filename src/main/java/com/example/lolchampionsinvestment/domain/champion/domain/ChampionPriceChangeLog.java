package com.example.lolchampionsinvestment.domain.champion.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ChampionPriceChangeLog")
@Getter
@NoArgsConstructor
public class ChampionPriceChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    @Column(name = "champion_id")
    private int champion_id;

    @Column(name = "before_price")
    private int beforePrice;

    @Column(name = "after_price")
    private int afterPrice;

    @Column(name = "create_date")
    private LocalDateTime create_date;

    @Builder
    public ChampionPriceChangeLog(int champion_id, int beforePrice, int afterPrice, LocalDateTime create_date) {
        this.champion_id = champion_id;
        this.beforePrice = beforePrice;
        this.afterPrice = afterPrice;
        this.create_date = create_date;
    }
}
