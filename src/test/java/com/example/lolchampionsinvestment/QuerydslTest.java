package com.example.lolchampionsinvestment;

import com.example.lolchampionsinvestment.config.QuerydslConfig;
import com.example.lolchampionsinvestment.domain.champion.Champion;
import com.example.lolchampionsinvestment.domain.champion.ChampionRepository;
import com.example.lolchampionsinvestment.domain.champion.QChampion;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(QuerydslConfig.class)
public class QuerydslTest {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    ChampionRepository championRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void init() {
        Champion aatrox = createChampion("Aatrox", 1000, "아트록스", LocalDateTime.now());
        Champion teemo = createChampion("Teemo", 2000, "티모", LocalDateTime.now());
        Champion ahri = createChampion("Ahri", 3000, "아리", LocalDateTime.now());

        championRepository.save(aatrox);
        championRepository.save(teemo);
        championRepository.save(ahri);
    }

    @DisplayName("Querydsl test >> 올바르게 insert되었는지 확인")
    @Test
    void selectBeforeInsertDataWithQuerydsl(){
        //given
        QChampion champion = new QChampion("champion");

        //when
        Champion result1 = jpaQueryFactory.select(champion)
                .from(champion)
                .where(champion.name.eq("Aatrox"))
                .fetchOne();
        Champion result2 = jpaQueryFactory.select(champion)
                .from(champion)
                .where(champion.name.eq("Teemo"))
                .fetchOne();
        Champion result3 = jpaQueryFactory.select(champion)
                .from(champion)
                .where(champion.name.eq("Ahri"))
                .fetchOne();

        //then
        assertThat(result1.getName()).isEqualTo("Aatrox");
        assertThat(result2.getPrice()).isEqualTo(2000);
        assertThat(result3.getDescription()).isEqualTo("아리");
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
