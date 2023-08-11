package com.example.lolchampionsinvestment.domain.champion.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Champion")
public class Champion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "champion_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "eng_name")
    private String eng_name;

    @Column(name = "price")
    private int price;

    @Column(name = "total_price")
    private int total_price;

    @Column(name = "img")
    private String img;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "create_date")
    private LocalDateTime createDateTime;

    @Column(name = "lastupdate_date")
    private LocalDateTime updateDateTime;


    @Builder
    public Champion(String name, String eng_name, int price, String img, String description, LocalDateTime createDateTime) {
        this.name = name;
        this.eng_name = eng_name;
        this.price = price;
        this.img = img;
        this.description = description;
        this.createDateTime = createDateTime;
    }
}
