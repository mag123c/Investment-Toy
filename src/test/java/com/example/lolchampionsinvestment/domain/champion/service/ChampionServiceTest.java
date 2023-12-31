package com.example.lolchampionsinvestment.domain.champion.service;

import com.example.lolchampionsinvestment.domain.champion.dao.ChampionRepository;
import com.example.lolchampionsinvestment.domain.champion.domain.Champion;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ChampionServiceTest {

    @Autowired
    ChampionService championService;
    @Autowired
    ChampionDataParsingService championDataParsingService;
    @Autowired
    ChampionRepository championRepository;

    @BeforeEach
    void beforeEach() {
        championDataParsingService.championsInsertTable();
    }

    @AfterEach
    void tearDown() {
        championRepository.deleteAllInBatch();
    }

    @DisplayName("json 데이터를 읽어온다.")
    @Test
    void getJsonData(){
        //given
        List<Map<String, Object>> list = championDataParsingService.championsMapping();

        //when //then
        assertThat(list).isNotEmpty();
        assertThat(list).hasSizeGreaterThan(0);

    }
    @DisplayName("현존하는 모든 챔피언 정보를 가져온다.")
    @Test
    void getAllChampionsData(){
        //given
        List<Map<String, Object>> jsonList = championDataParsingService.championsMapping();

        //when //then
        assertThat(championRepository.findAll().size()).isEqualTo(jsonList.size());
    }

    @DisplayName("가격이 가장 높은 챔피언 정보와 가장 낮은 챔피언 정보를 가져온다")
    @Test
    void getChampionDataWithHighestPriceAndLowestPrice() {
        //given
        Champion highestPriceChampion = championRepository.findByPriceInDesc();
        Champion lowestPriceChampion = championRepository.findByPriceInAsc();

        //when
        int highestPrice = highestPriceChampion.getPrice();
        int lowestPrice = lowestPriceChampion.getPrice();

        //then
        assertThat(highestPrice).as("highestPrice : " + highestPrice
                        + ", lowestPrice : " + lowestPrice)
                .isGreaterThan(lowestPrice);
    }
}