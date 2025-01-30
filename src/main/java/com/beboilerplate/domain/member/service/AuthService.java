package com.beboilerplate.domain.member.service;

import com.beboilerplate.domain.member.dto.request.LoginRequest;
import com.beboilerplate.domain.member.dto.request.SignUpRequest;
import com.beboilerplate.domain.member.dto.response.LoginResponse;
import com.beboilerplate.domain.member.dto.response.MemberResponse;
import com.beboilerplate.domain.member.entity.Member;
import com.beboilerplate.domain.member.exception.PasswordMismatchException;
import com.beboilerplate.domain.member.repository.MemberRepository;
import com.beboilerplate.global.config.redis.RedisService;
import com.beboilerplate.global.config.s3.S3Service;
import com.beboilerplate.global.exception.EntityNotFoundException;
import com.beboilerplate.global.jwt.JwtTokenProvider;
import com.beboilerplate.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String FILE_TYPE = "profile";
    private final String REFRESH_TOKEN_PREFIX = "refresh:";

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    @Transactional
    public Long signup(SignUpRequest signUpRequest, MultipartFile multipartFile) {
        Member generatedMember = signUpRequest.toEntity(passwordEncoder);
        String profileImageUrl = s3Service.uploadFile(FILE_TYPE, multipartFile);
        generatedMember.setProfileImageUrl(profileImageUrl);
        return memberRepository.save(generatedMember).getId();
    }

    @Transactional
    public LoginResponse normalLogin(LoginRequest loginRequest) {

        Member member = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        validatePassword(loginRequest.getPassword(), member.getPassword());
        String accessToken = jwtTokenProvider.createAccessToken(member.getEmail(), member.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail());
        redisService.setValues(REFRESH_TOKEN_PREFIX + member.getEmail(), refreshToken);

        return new LoginResponse(accessToken, refreshToken, MemberResponse.from(member));
    }

    private void validatePassword(String requestPassword, String memberPassword) {
        if (!passwordEncoder.matches(requestPassword, memberPassword)) {
            throw new PasswordMismatchException();
        }
    }
}
