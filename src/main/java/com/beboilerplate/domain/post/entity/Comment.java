package com.beboilerplate.domain.post.entity;

import com.beboilerplate.domain.member.entity.Member;
import com.beboilerplate.global.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column
    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    @OneToMany(mappedBy = "parent")
    private List<Comment> replies = new ArrayList<>();

    @Builder
    public Comment(Comment parent, Post post, Member author, String content) {
        this.parent = parent;
        this.post = post;
        this.author = author;
        this.content = content;
    }

    public void softDelete() {
        this.deleted = true;
    }

    public void update(String content) {
        this.content = content;
    }

    public String getContent() {
        return deleted ? "삭제된 메시지입니다." : this.content;
    }
}
