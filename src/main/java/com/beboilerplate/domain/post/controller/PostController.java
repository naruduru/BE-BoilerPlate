package com.beboilerplate.domain.post.controller;

import com.beboilerplate.domain.post.dto.request.PostRequest;
import com.beboilerplate.domain.post.service.PostService;
import com.beboilerplate.global.response.SuccessCode;
import com.beboilerplate.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Posts", description = "게시글 API")
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "게시글 작성")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> addPost(
            @RequestPart PostRequest postRequest,
            @RequestPart(name = "multipartFiles", required = false) List<MultipartFile> multipartFiles
    ) {
        return ResponseEntity.ok(SuccessResponse.of(
                SuccessCode.ADD_POST_SUCCESS,
                postService.addPost(postRequest, multipartFiles))
        );
    }

    @Operation(summary = "게시글 목록 조회")
    @GetMapping
    public ResponseEntity<SuccessResponse> getPosts(@RequestParam int pageNumber, @RequestParam int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("updatedAt").descending());

        return ResponseEntity.ok(SuccessResponse.of(
                SuccessCode.GET_POSTS_SUCCESS,
                postService.getPosts(pageable))
        );
    }

    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<SuccessResponse> getPostDetail(
            @PathVariable("postId") Long postId, HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(SuccessResponse.of(
                SuccessCode.GET_POST_DETAIL_SUCCESS,
                postService.getPostDetail(postId, request, response))
        );
    }

    @Operation(summary = "게시글 수정")
    @PutMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> updatePost(
            @PathVariable Long postId,
            @RequestPart PostRequest postRequest,
            @RequestPart(name = "multipartFiles", required = false) List<MultipartFile> multipartFiles
    ) {
        return ResponseEntity.ok(SuccessResponse.of(
                SuccessCode.UPDATE_POST_SUCCESS,
                postService.updatePost(postId, postRequest, multipartFiles))
        );
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<SuccessResponse> deletePost(@PathVariable Long postId) {
        return ResponseEntity.ok(SuccessResponse.of(
                SuccessCode.DELETE_POST_SUCCESS,
                postService.deletePost(postId))
        );
    }

    @Operation(summary = "게시글 좋아요/좋아요 취소")
    @PostMapping("/{postId}/likes")
    public ResponseEntity<SuccessResponse> likePost(@PathVariable Long postId) {
        return ResponseEntity.ok(SuccessResponse.of(
                SuccessCode.LIKE_POST_SUCCESS,
                postService.likePost(postId)
        ));
    }
}
