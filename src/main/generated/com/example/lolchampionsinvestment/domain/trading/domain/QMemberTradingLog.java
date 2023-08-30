package com.example.lolchampionsinvestment.domain.trading.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberTradingLog is a Querydsl query type for MemberTradingLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberTradingLog extends EntityPathBase<MemberTradingLog> {

    private static final long serialVersionUID = 1810660121L;

    public static final QMemberTradingLog memberTradingLog = new QMemberTradingLog("memberTradingLog");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final NumberPath<Integer> champion_id = createNumber("champion_id", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> create_date = createDateTime("create_date", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<TradingType> type = createEnum("type", TradingType.class);

    public final NumberPath<Integer> user_id = createNumber("user_id", Integer.class);

    public QMemberTradingLog(String variable) {
        super(MemberTradingLog.class, forVariable(variable));
    }

    public QMemberTradingLog(Path<? extends MemberTradingLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberTradingLog(PathMetadata metadata) {
        super(MemberTradingLog.class, metadata);
    }

}

