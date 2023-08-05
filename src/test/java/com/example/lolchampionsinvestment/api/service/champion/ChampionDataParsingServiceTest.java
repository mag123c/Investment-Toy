package com.example.lolchampionsinvestment.api.service.champion;

import com.example.lolchampionsinvestment.domain.champion.dao.ChampionRepository;
import com.example.lolchampionsinvestment.domain.champion.service.ChampionDataParsingService;
import jakarta.transaction.Transactional;
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
class ChampionDataParsingServiceTest {

    @BeforeEach
    void beforeEach() {

    }
    @Autowired
    ChampionDataParsingService championDataParsingService;
    @Autowired
    ChampionRepository championRepository;

    @DisplayName("json 데이터를 읽어온다.")
    @Test
    void test(){
        //given
        List<Map<String, Object>> list = championDataParsingService.championsMapping();

        //when //then
        assertThat(list).isNotEmpty();
        assertThat(list).hasSizeGreaterThan(0);

    }

    @DisplayName("json파일을 읽어와서 테이블에 챔피언 정보를 등록한다.")
    @Test
    void getJsonDataAndTableInsert(){
        //given
        List<Map<String, Object>> championList = championDataParsingService.championsMapping();
        int listSize = championList.size();
        int beforeTableSize = championRepository.findAll().size();

        //when
        championDataParsingService.championsInsertTable();
        int afterTableSize = championRepository.findAll().size();

        //then

        assertThat(afterTableSize).isEqualTo(listSize);
        assertThat(beforeTableSize).isEqualTo(afterTableSize - championList.size());

    }

}