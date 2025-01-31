package com.beboilerplate.domain.post.controller;

import com.beboilerplate.domain.post.dto.request.CommentRequest;
import com.beboilerplate.domain.post.service.CommentService;
import com.beboilerplate.global.response.SuccessCode;
import com.beboilerplate.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comments", description = "댓글 API")
@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "댓글 작성")
    @PostMapping
    public ResponseEntity<SuccessResponse> addComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest commentRequest
    ) {
        return ResponseEntity.ok(SuccessResponse.of(
                SuccessCode.ADD_COMMENT_SUCCESS,
                commentService.addComment(postId, commentRequest))
        );
    }

    @Operation(summary = "대댓글 작성")
    @PostMapping("/{commentId}/replies")
    public ResponseEntity<SuccessResponse> addReply(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentRequest commentRequest
    ) {
        return ResponseEntity.ok(SuccessResponse.of(
                SuccessCode.ADD_REPLY_SUCCESS,
                commentService.addReply(postId, commentId, commentRequest))
        );
    }

    @Operation(summary = "댓글 목록 조회")
    @GetMapping
    public ResponseEntity<SuccessResponse> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(SuccessResponse.of(
                SuccessCode.GET_COMMENTS_SUCCESS,
                commentService.getComments(postId))
        );
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    public ResponseEntity<SuccessResponse> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentRequest commentRequest
    ) {
        return ResponseEntity.ok(SuccessResponse.of(
                SuccessCode.UPDATE_COMMENT_SUCCESS,
                commentService.updateComment(postId, commentId, commentRequest))
        );
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<SuccessResponse> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        return ResponseEntity.ok(SuccessResponse.of(
                SuccessCode.DELETE_COMMENT_SUCCESS,
                commentService.deleteComment(postId, commentId))
        );
    }
}
