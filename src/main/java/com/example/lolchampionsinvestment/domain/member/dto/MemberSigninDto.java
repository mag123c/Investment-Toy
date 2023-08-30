package com.example.lolchampionsinvestment.domain.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MemberSigninDto {

    private String userId;
    private String pw;

    @Builder
    public MemberSigninDto(String userId, String pw) {
        this.userId = userId;
        this.pw = pw;
    }

}
