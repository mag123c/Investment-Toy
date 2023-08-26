package com.example.lolchampionsinvestment.domain.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

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
    private LocalDateTime update_date;

    @Builder
    public Member(String userId, String pw, String nickname, int cash, LocalDateTime create_date, LocalDateTime update_date) {
        this.userId = userId;
        this.pw = pw;
        this.nickname = nickname;
        this.cash = cash;
        this.create_date = create_date;
        this.update_date = update_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return cash == member.cash &&
                Objects.equals(userId, member.userId) &&
                Objects.equals(pw, member.pw) &&
                Objects.equals(nickname, member.nickname) &&
                Objects.equals(create_date, member.create_date) &&
                Objects.equals(update_date, member.update_date);
    }
    @Override
    public int hashCode() {
        return Objects.hash(userId, pw, nickname, cash, create_date, update_date);
    }
}
