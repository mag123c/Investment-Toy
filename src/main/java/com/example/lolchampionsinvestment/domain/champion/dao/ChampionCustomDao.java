package com.example.lolchampionsinvestment.domain.champion.dao;

import com.example.lolchampionsinvestment.domain.champion.domain.QChampion;
import com.example.lolchampionsinvestment.domain.champion.domain.QChampionPriceLog;
import com.example.lolchampionsinvestment.domain.champion.dto.ChampionPriceDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.lolchampionsinvestment.domain.champion.domain.QChampion.champion;


@Repository
@RequiredArgsConstructor
public class ChampionCustomDao {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    public List<ChampionPriceDto> findAllLatestChampions() {
        QChampion C = champion;
        QChampionPriceLog CPL = QChampionPriceLog.championPriceLog;

        List<Tuple> tupleList = jpaQueryFactory.select(C.name, CPL.price, C.price, C.total_price)
                .from(CPL)
                .join(C)
                .on(CPL.champion_id.eq(C.id.intValue()))
                .where(
                        Expressions.list(CPL.champion_id, CPL.create_date)
                                .in(
                                        jpaQueryFactory.select(
                                                        CPL.champion_id, CPL.create_date.max().as("create_date")
                                                )
                                                .from(CPL)
                                                .groupBy(CPL.champion_id)
                                )

                )
                .orderBy(C.total_price.desc())
                .fetch();

        return tupleList.stream()
                .map(tuple -> {
                    String name = tuple.get(C.name);
                    int price = tuple.get(C.price);
                    int cplPrice = tuple.get(CPL.price);
                    int totalPrice = tuple.get(C.total_price);
                    int percent = Math.round((price - cplPrice) * 100 / cplPrice);
                    return new ChampionPriceDto(name, price, totalPrice, percent);
                })
                .collect(Collectors.toList());
    }

    public void championPriceUpdate(String name, int price) {
        jpaQueryFactory.update(champion)
                .set(champion.name, name).set(champion.price, price)
                .where(champion.name.eq(name))
                .execute();

        em.flush();
        em.clear();
    }

}
