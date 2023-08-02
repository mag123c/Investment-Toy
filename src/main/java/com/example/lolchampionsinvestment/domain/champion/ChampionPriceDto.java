package com.example.lolchampionsinvestment.domain.champion;

import lombok.Data;

@Data
public class ChampionPriceDto {

    private String name;

    private int price;

    private int percent;

    public ChampionPriceDto(String name, int price, int percent) {
        this.name = name;
        this.price = price;
        this.percent = percent;
    }
}
