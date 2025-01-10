package com.beboilerplate.global.util;

import com.beboilerplate.domain.member.entity.Member;
import com.beboilerplate.domain.member.repository.MemberRepository;
import com.beboilerplate.global.exception.EntityNotFoundException;
import com.beboilerplate.global.response.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    private final MemberRepository memberRepository;

    @Autowired
    public AuthUtil(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getLoginMember() {
        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}

