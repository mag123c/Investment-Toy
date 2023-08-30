package com.example.lolchampionsinvestment.domain.trading.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TradingType {

    BUY("구매"),
    SELL("판매");

    private final String type;

}
