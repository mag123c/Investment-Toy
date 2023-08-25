package com.example.lolchampionsinvestment.domain.trading.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "MemberChampion")
@NoArgsConstructor
public class MemberChampion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_champion_id")
    private long id;

    @Column(name = "member_id")
    private int member_id;

    @Column(name = "champion_id")
    private int champion_id;

    @Column(name = "amount")
    private int amount;

    @Builder
    public MemberChampion(int member_id, int champion_id, int amount) {
        this.member_id = member_id;
        this.champion_id = champion_id;
        this.amount = amount;
    }
}
