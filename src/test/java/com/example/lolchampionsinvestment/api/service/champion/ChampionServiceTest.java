package com.example.lolchampionsinvestment.api.service.champion;

import com.example.lolchampionsinvestment.domain.champion.dao.ChampionRepository;
import com.example.lolchampionsinvestment.domain.champion.service.ChampionDataParsingService;
import com.example.lolchampionsinvestment.domain.champion.service.ChampionService;
import jakarta.transaction.Transactional;
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

        // when
        championDataParsingService.championsInsertTable();
        // then
        assertThat(championRepository.findAll().size()).isEqualTo(jsonList.size());
    }
}