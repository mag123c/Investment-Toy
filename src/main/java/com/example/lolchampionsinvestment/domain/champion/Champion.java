package com.example.lolchampionsinvestment.domain.champion;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    private Long id;

    @Column(name = "name")
    private String name;

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
    public Champion(String name, int price, String description, LocalDateTime createDateTime) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.createDateTime = createDateTime;
    }
}
