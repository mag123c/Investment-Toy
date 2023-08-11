package com.example.lolchampionsinvestment.domain.champion.dto;

import lombok.Data;

@Data
public class ChampionPriceDto {

    private String name;

    private int price;

    private int totalPrice;

    private int percent;

    public ChampionPriceDto(String name, int price, int totalPrice, int percent) {
        this.name = name;
        this.price = price;
        this.totalPrice = totalPrice;
        this.percent = percent;
    }
}
