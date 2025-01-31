package com.beboilerplate.domain.member.controller;

import com.beboilerplate.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Tag(name = "유저 API", description = "회원가입, 로그인")
public class MemberController {

    private final MemberService memberService;


}
