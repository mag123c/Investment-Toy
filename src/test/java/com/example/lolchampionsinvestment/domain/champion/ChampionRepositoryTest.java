package com.example.lolchampionsinvestment.domain.champion;

import com.example.lolchampionsinvestment.domain.champion.dao.ChampionRepository;
import com.example.lolchampionsinvestment.domain.champion.domain.Champion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ChampionRepositoryTest {

    @Autowired
    private ChampionRepository championRepository;

    @DisplayName("챔피언을 등록한다.")
    @Test
    void test(){
        //given
        Champion Aatrox = createChampion("Aatrox", 1000, "설명", LocalDateTime.now());
        Champion Teemo = createChampion("Teemo", 10, "설명", LocalDateTime.now());
        int beforeSize = championRepository.findAll().size();

        championRepository.saveAll(List.of(Aatrox, Teemo));

        //when
        int currentSize = championRepository.findAllByNameIn(List.of("Aatrox","Teemo")).size();

        //then
        assertEquals(beforeSize + 2, currentSize);

    }

    private static Champion createChampion(String name, int price, String description, LocalDateTime createDateTime) {
        return Champion.builder()
                .name(name)
                .price(price)
                .description(description)
                .createDateTime(createDateTime)
                .build();
    }

}