package com.example.lolchampionsinvestment.domain.champion.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ChampionPriceLog")
@Getter
@NoArgsConstructor
public class ChampionPriceLog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "champion_price_id")
    private Long id;

    @Column(name = "champion_id")
    private Long champion_id;

    @Column(name = "price")
    private int price;

    @Column(name = "create_date")
    private LocalDateTime create_date;

    @Builder
    public ChampionPriceLog(Long champion_id, int price, LocalDateTime create_date) {
        this.champion_id = champion_id;
        this.price = price;
        this.create_date = create_date;
    }
}
