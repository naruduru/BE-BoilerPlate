package com.beboilerplate.domain.member.entity;

import com.beboilerplate.domain.member.entity.enums.LoginType;
import com.beboilerplate.domain.member.entity.enums.Role;
import com.beboilerplate.global.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)", unique = true, nullable = false)
    private String email;

    @Column
    private String password;

    @Column
    private String socialId;

    @Column(unique = true)
    private String nickname;

    @Column
    @Setter
    private String profileImageUrl;

    @Column(columnDefinition = "VARCHAR(1)")
    private String useYn;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Builder
    public Member(String email, String password, String nickname,
                  String profileImageUrl, String useYn, Role role, LoginType loginType) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.useYn = useYn;
        this.role = role;
        this.loginType = loginType;
    }
}
