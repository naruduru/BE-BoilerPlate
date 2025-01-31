package com.beboilerplate.domain.post.controller;

import com.beboilerplate.domain.post.dto.request.PostRequest;
import com.beboilerplate.domain.post.service.PostService;
import com.beboilerplate.global.response.SuccessCode;
import com.beboilerplate.global.response.SuccessResponse;
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

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> register(
            @RequestPart PostRequest postRequest,
            @RequestPart(name = "multipartFiles", required = false) List<MultipartFile> multipartFiles
    ) {
        return ResponseEntity.ok(SuccessResponse.of(
                SuccessCode.REGISTER_POST_SUCCESS,
                postService.register(postRequest, multipartFiles))
        );
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getPosts(@RequestParam int pageNumber, @RequestParam int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("updatedAt").descending());

        return ResponseEntity.ok(SuccessResponse.of(
                SuccessCode.CREATE_ROOM_SUCCESS,
                postService.getPosts(pageable))
        );
    }

    @GetMapping("/{postId}")
    public ResponseEntity<SuccessResponse> getPostDetail(
            @PathVariable("postId") Long postId, HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(SuccessResponse.of(
                SuccessCode.GET_POST_DETAIL_SUCCESS,
                postService.getPostDetail(postId, request, response))
        );
    }
}
