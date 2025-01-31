package com.beboilerplate.domain.post.dto.response;

import com.beboilerplate.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Builder
public class PostSimpleResponse {

    private Long postId;
    private String author;
    private String title;
    private long viewCount;
    private long likeCount;
    private String createdAt;
    private String updatedAt;

    public static PostSimpleResponse from(Post post) {
        return PostSimpleResponse.builder()
                .postId(post.getId())
                .author(post.getAuthor().getNickname())
                .title(post.getTitle())
                .viewCount(post.getViewCount())
                .likeCount(post.getPostLikes().size())
                .createdAt(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(post.getCreatedAt()))
                .updatedAt(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(post.getUpdatedAt()))
                .build();
    }
}
