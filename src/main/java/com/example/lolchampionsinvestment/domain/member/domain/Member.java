package com.example.lolchampionsinvestment.domain.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Member")
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;

    @Column(name = "id")
    @Size(min = 4, max = 20)
    private String userId;

    @Column(name = "pw")
    private String pw;

    @Column(name = "nickname")
    @Size(min = 4, max = 20)
    private String nickname;

    @Column(name = "cash")
    private int cash;

    @Column(name = "create_date")
    private LocalDateTime create_date;

    @Column(name = "lastupdate_date")
    private LocalDateTime lastupdate_date;

    @Builder
    public Member(String userId, String pw, String nickname, int cash, LocalDateTime create_date) {
        this.userId = userId;
        this.pw = pw;
        this.nickname = nickname;
        this.cash = cash;
        this.create_date = create_date;
    }

    @Builder
    public Member(String userId, String pw, String nickname, int cash, LocalDateTime create_date, LocalDateTime lastupdate_date) {
        this.userId = userId;
        this.pw = pw;
        this.nickname = nickname;
        this.cash = cash;
        this.create_date = create_date;
        this.lastupdate_date = lastupdate_date;
    }
}
