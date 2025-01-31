package com.beboilerplate.domain.post.dto.response;

import com.beboilerplate.domain.post.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CommentResponse {
    private Long commentId;
    private Long postId;
    private String author;
    private String content;
    private boolean deleted;
    private List<CommentResponse> replies;
    private String createdAt;
    private String updatedAt;

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .postId(comment.getPost().getId())
                .author(comment.getAuthor().getNickname())
                .content(comment.getContent())
                .deleted(comment.isDeleted())
                .replies(comment.getReplies().stream()
                        .map(CommentResponse::from)
                        .collect(Collectors.toList()))
                .createdAt(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(comment.getCreatedAt()))
                .updatedAt(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(comment.getUpdatedAt()))
                .build();
    }

}
