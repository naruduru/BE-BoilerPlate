package com.beboilerplate.domain.post.dto.response;

import com.beboilerplate.domain.post.entity.Post;
import com.beboilerplate.domain.post.entity.PostImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Builder
public class PostDetailResponse {

    private Long postId;
    private String author;
    private String title;
    private String content;
    private long viewCount;
    private String createdAt;
    private String updatedAt;
    private List<String> postImageUrls;

    public static PostDetailResponse from(Post post) {
        return PostDetailResponse.builder()
                .postId(post.getId())
                .author(post.getAuthor().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .createdAt(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(post.getCreatedAt()))
                .updatedAt(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(post.getUpdatedAt()))
                .postImageUrls(post.getPostImages().stream().map(PostImage::getUrl).toList())
                .build();
    }
}
