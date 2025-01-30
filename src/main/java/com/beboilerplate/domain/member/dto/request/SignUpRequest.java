package com.beboilerplate.domain.member.dto.request;

import com.beboilerplate.domain.member.entity.Member;
import com.beboilerplate.domain.member.entity.enums.LoginType;
import com.beboilerplate.domain.member.entity.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class SignUpRequest {

    @Schema(description = "이메일", example = "test@naver.com")
    private String email;
    @Schema(description = "비밀번호", example = "test")
    private String password;
    @Schema(description = "닉네임", example = "test")
    private String nickname;

    public Member toEntity(PasswordEncoder passwordEncoder) {

        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .loginType(LoginType.NORMAL)
                .role(Role.ROLE_USER)
                .useYn("Y")
                .build();
    }
}
