package com.example.lolchampionsinvestment;

import com.example.lolchampionsinvestment.domain.champion.Champion;
import com.example.lolchampionsinvestment.domain.champion.ChampionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DBConnectionTest {

    @Autowired
    private ChampionRepository championRepository;

    @DisplayName("MYSQL 연결상태를 확인하여 Member 테이블을 조회한다.")
    @Test
    void test(){
        //given
        List<Champion> championList = championRepository.findAll();

        //when //then
        Assertions.assertEquals(championList.size(), 0);

    }

}
