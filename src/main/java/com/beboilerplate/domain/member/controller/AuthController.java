package com.beboilerplate.domain.member.controller;

import com.beboilerplate.domain.member.dto.request.LoginRequest;
import com.beboilerplate.domain.member.dto.request.SignUpRequest;
import com.beboilerplate.domain.member.dto.response.LoginResponse;
import com.beboilerplate.domain.member.service.AuthService;
import com.beboilerplate.global.response.SuccessCode;
import com.beboilerplate.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "인증 API")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "회원가입")
    @PostMapping(value = "/signup/normal", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> signup(
            @RequestPart SignUpRequest signUpRequest,
            @RequestPart(required = false) MultipartFile multipartFile
    ) {
        Long memberId = authService.signup(signUpRequest, multipartFile);

        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SIGN_UP_SUCCESS, memberId));
    }

    @Operation(summary = "일반 로그인")
    @PostMapping("/login/normal")
    public ResponseEntity<SuccessResponse> normalLogin(@RequestBody LoginRequest loginRequest) {
        LoginResponse result = authService.normalLogin(loginRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.LOGIN_SUCCESS, result));
    }
}
