package com.example.lolchampionsinvestment.domain.champion.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChampion is a Querydsl query type for Champion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChampion extends EntityPathBase<Champion> {

    private static final long serialVersionUID = -325199985L;

    public static final QChampion champion = new QChampion("champion");

    public final DateTimePath<java.time.LocalDateTime> createDateTime = createDateTime("createDateTime", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath eng_name = createString("eng_name");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath img = createString("img");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> total_price = createNumber("total_price", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> updateDateTime = createDateTime("updateDateTime", java.time.LocalDateTime.class);

    public QChampion(String variable) {
        super(Champion.class, forVariable(variable));
    }

    public QChampion(Path<? extends Champion> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChampion(PathMetadata metadata) {
        super(Champion.class, metadata);
    }

}

