package com.beboilerplate.domain.post.dto.response;

import com.beboilerplate.domain.post.entity.PostLike;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class PostLikeResponse {
    private Long postLikeId;
    private String memberNickname;
    private String likedAt;

    public static PostLikeResponse from(PostLike postLike) {
        return PostLikeResponse.builder()
                .postLikeId(postLike.getId())
                .memberNickname(postLike.getMember().getNickname())
                .likedAt(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(postLike.getLikedAt()))
                .build();
    }
}
