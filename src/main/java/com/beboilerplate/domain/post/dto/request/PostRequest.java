package com.beboilerplate.domain.post.dto.request;

import com.beboilerplate.domain.member.entity.Member;
import com.beboilerplate.domain.post.entity.Post;
import lombok.Data;

@Data
public class PostRequest {

    private String title;
    private String content;

    public Post toEntity(Member author) {
        return Post.builder()
                .author(author)
                .title(title)
                .content(content)
                .build();
    }
}
