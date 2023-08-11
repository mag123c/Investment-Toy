package com.example.lolchampionsinvestment.domain.champion.service;

import com.example.lolchampionsinvestment.domain.champion.dao.ChampionCustomDao;
import com.example.lolchampionsinvestment.domain.champion.dao.ChampionRepository;
import com.example.lolchampionsinvestment.domain.champion.domain.Champion;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class ChampionRandomUpdater {

    @Autowired
    ChampionRepository championRepository;

    @Autowired
    ChampionCustomDao championCustomDao;

    private Map<String, Long> championsDelays = new HashMap<>();
    private Map<String, Integer> championsPrices = new HashMap<>();
    private Random random = new Random();


    @PostConstruct
    public void initChampionDelays() {
        List<Champion> championList = championRepository.findAll();
        for(Champion champion : championList) {
            championsDelays.put(champion.getName(), generateRandomDelay());
            championsPrices.put(champion.getName(), champion.getPrice());
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void updateChampionsPrice() {
        for(Map.Entry<String, Long> championMap : championsDelays.entrySet()) {
            String championName = championMap.getKey();
            long delay = championMap.getValue();
            int currentPrice = championsPrices.get(championName);

            if(delay <= 0) {
                int newPrice = generateRandomPrice(currentPrice);
                championsPrices.put(championName, newPrice);
                championCustomDao.championPriceUpdate(championName, currentPrice + newPrice);

                championsDelays.put(championName, generateRandomDelay());
            }
            else {
                championsDelays.put(championName, delay - 1000);
            }
        }
    }

    private int generateRandomPrice(int currentPrice) {
        return (currentPrice / 100) * (random.nextInt(11) - 5);
    }

    private long generateRandomDelay() {
        long minDelayMillis = 5000;
        long maxDelayMillis = 3600000;

        return minDelayMillis + random.nextLong() % (maxDelayMillis - minDelayMillis + 1);
    }

}
