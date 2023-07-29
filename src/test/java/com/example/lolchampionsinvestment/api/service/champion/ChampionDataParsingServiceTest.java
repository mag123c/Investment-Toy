package com.example.lolchampionsinvestment.api.service.champion;

import com.example.lolchampionsinvestment.domain.champion.Champion;
import com.example.lolchampionsinvestment.domain.champion.ChampionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ChampionDataParsingServiceTest {

    @Autowired
    ChampionDataParsingService championDataParsingService;
    @Autowired
    ChampionRepository championRepository;

    @DisplayName("json파일을 읽어와서 테이블에 챔피언 정보를 등록한다.")
    @Test
    void test(){
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