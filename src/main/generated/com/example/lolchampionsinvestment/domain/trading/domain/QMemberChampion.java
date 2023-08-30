package com.example.lolchampionsinvestment.domain.trading.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberChampion is a Querydsl query type for MemberChampion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberChampion extends EntityPathBase<MemberChampion> {

    private static final long serialVersionUID = 1351118495L;

    public static final QMemberChampion memberChampion = new QMemberChampion("memberChampion");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final NumberPath<Integer> champion_id = createNumber("champion_id", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> member_id = createNumber("member_id", Integer.class);

    public QMemberChampion(String variable) {
        super(MemberChampion.class, forVariable(variable));
    }

    public QMemberChampion(Path<? extends MemberChampion> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberChampion(PathMetadata metadata) {
        super(MemberChampion.class, metadata);
    }

}

