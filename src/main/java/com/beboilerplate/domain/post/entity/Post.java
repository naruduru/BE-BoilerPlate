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
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private int viewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<PostImage> postImages = new ArrayList<>();

    @Builder
    public Post(Member author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public void addViewCount() {
        viewCount++;
    }
}
