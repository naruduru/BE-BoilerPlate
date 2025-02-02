package com.beboilerplate.domain.post.entity;

import com.beboilerplate.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime likedAt;

    public PostLike(Member member, Post post) {
        this.member = member;
        this.post = post;
        this.likedAt = LocalDateTime.now();
        member.getPostLikes().add(this);
        post.getPostLikes().add(this);
    }
}
