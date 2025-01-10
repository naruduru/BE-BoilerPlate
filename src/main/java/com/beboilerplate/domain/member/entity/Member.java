package com.beboilerplate.domain.member.entity;

import com.beboilerplate.global.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)", unique = true, nullable = false)
    private String email;

    @Column(columnDefinition = "VARCHAR(10)", unique = true)
    private String nickname;

    private String profileImageUrl;

    @Column(columnDefinition = "VARCHAR(1)")
    private String useYn;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Builder
    public Member(String email, String nickname, String profileImageUrl,
                  String useYn, Role role, LoginType loginType) {
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.useYn = useYn;
        this.role = role;
        this.loginType = loginType;
    }
}
