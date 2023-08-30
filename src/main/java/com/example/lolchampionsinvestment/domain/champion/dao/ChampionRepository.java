package com.example.lolchampionsinvestment.domain.champion.dao;

import com.example.lolchampionsinvestment.domain.champion.domain.Champion;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChampionRepository extends JpaRepository<Champion, Long> {

    List<Champion> findAllByNameIn(List<String> name);
    List<Champion> findAllByOrderByNameAsc();
    Optional<Champion> findByName(String name);
    @Query("select c from Champion c order by c.price asc limit 1")
    Champion findByPriceInAsc();
    @Query("select c from Champion c order by c.price desc limit 1")
    Champion findByPriceInDesc();
}
