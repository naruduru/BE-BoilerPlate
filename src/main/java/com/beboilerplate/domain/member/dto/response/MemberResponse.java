package com.beboilerplate.domain.member.dto.response;

import com.beboilerplate.domain.member.entity.LoginType;
import com.beboilerplate.domain.member.entity.Member;
import com.beboilerplate.domain.member.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {
    private Long id;
    private String email;
    private String nickname;
    private String profileImageUrl;
    private String useYn;
    private Role role;
    private LoginType loginType;

    public static MemberResponse from(Member member) {

        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImageUrl(member.getProfileImageUrl())
                .useYn(member.getUseYn())
                .role(member.getRole())
                .loginType(member.getLoginType())
                .build();
    }

}
