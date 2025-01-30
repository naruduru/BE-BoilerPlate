package com.beboilerplate.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private MemberResponse memberResponse;
}
