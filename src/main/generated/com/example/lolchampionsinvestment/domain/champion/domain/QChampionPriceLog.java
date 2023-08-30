package com.example.lolchampionsinvestment.domain.champion.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChampionPriceLog is a Querydsl query type for ChampionPriceLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChampionPriceLog extends EntityPathBase<ChampionPriceLog> {

    private static final long serialVersionUID = 147080682L;

    public static final QChampionPriceLog championPriceLog = new QChampionPriceLog("championPriceLog");

    public final NumberPath<Integer> champion_id = createNumber("champion_id", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> create_date = createDateTime("create_date", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QChampionPriceLog(String variable) {
        super(ChampionPriceLog.class, forVariable(variable));
    }

    public QChampionPriceLog(Path<? extends ChampionPriceLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChampionPriceLog(PathMetadata metadata) {
        super(ChampionPriceLog.class, metadata);
    }

}

