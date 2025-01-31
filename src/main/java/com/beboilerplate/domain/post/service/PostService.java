package com.beboilerplate.domain.post.service;

import com.beboilerplate.domain.member.entity.Member;
import com.beboilerplate.domain.post.dto.request.PostRequest;
import com.beboilerplate.domain.post.dto.response.PostDetailResponse;
import com.beboilerplate.domain.post.dto.response.PostLikeResponse;
import com.beboilerplate.domain.post.dto.response.PostSimpleResponse;
import com.beboilerplate.domain.post.entity.Post;
import com.beboilerplate.domain.post.entity.PostImage;
import com.beboilerplate.domain.post.entity.PostLike;
import com.beboilerplate.domain.post.repository.PostLikeRepository;
import com.beboilerplate.domain.post.repository.PostRepository;
import com.beboilerplate.global.config.s3.S3Service;
import com.beboilerplate.global.exception.EntityNotFoundException;
import com.beboilerplate.global.exception.IllegalArgumentException;
import com.beboilerplate.global.response.ErrorCode;
import com.beboilerplate.global.util.AuthUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private static final String FILE_TYPE = "post";
    
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final AuthUtil authUtil;
    private final S3Service s3Service;

    @Transactional
    public Long addPost(PostRequest postRequest, List<MultipartFile> images) {
        Member loginMember = authUtil.getLoginMember();
        Post createdPost = postRequest.toEntity(loginMember);
        if (images != null && !images.isEmpty()) {
            List<String> uploadedImageUrls = new ArrayList<>();
            for (MultipartFile image : images) {
                uploadedImageUrls.add(uploadImage(image));
            }

            for (String uploadedImageUrl : uploadedImageUrls) {
                PostImage postImage = new PostImage(uploadedImageUrl);
                postImage.attachPost(createdPost);
            }
        }

        return postRepository.save(createdPost).getId();
    }

    @Transactional(readOnly = true)
    public Page<PostSimpleResponse> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostSimpleResponse::from);
    }

    @Transactional
    public PostDetailResponse getPostDetail(Long postId, HttpServletRequest req, HttpServletResponse res) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));
        addCookieForViewCount(post, req, res);

        return PostDetailResponse.from(post);
    }

    @Transactional
    public PostDetailResponse updatePost(Long postId, PostRequest postRequest, List<MultipartFile> images) {
        Member loginMember = authUtil.getLoginMember();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));
        validatePostOfMember(loginMember.getId(), post.getAuthor().getId());
        post.update(postRequest.getTitle(), postRequest.getContent());

        return PostDetailResponse.from(post);
    }

    @Transactional
    public PostDetailResponse deletePost(Long postId) {
        Member loginMember = authUtil.getLoginMember();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));
        validatePostOfMember(loginMember.getId(), post.getAuthor().getId());
        post.softDelete();

        return PostDetailResponse.from(post);
    }

    @Transactional
    public PostDetailResponse likePost(Long postId) {
        Member loginMember = authUtil.getLoginMember();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));
        if (loginMember.getPostLikes().stream().anyMatch(postLike -> postLike.getPost().equals(post))) {
            postLikeRepository.deleteByMemberAndPost(loginMember, post);
        } else {
            postLikeRepository.save(new PostLike(loginMember, post));
        }

        return PostDetailResponse.from(post);
    }

    private String uploadImage(MultipartFile image) {
        return s3Service.uploadFile(FILE_TYPE, image);
    }

    private void addCookieForViewCount(Post post, HttpServletRequest req, HttpServletResponse res) {
        // 현재 요청에 담긴 쿠키 목록
        Cookie[] cookies = req.getCookies();
        // postView 쿠키를 찾아올 변수
        Cookie postViewCookie = null;

        // 1) 기존 postView 쿠키가 있는지 확인
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("postView".equals(cookie.getName())) {
                    postViewCookie = cookie;
                    break;
                }
            }
        }

        // 2) postView 쿠키가 없다면 => 새로 생성 + 조회수 증가
        if (postViewCookie == null) {
            post.addViewCount();
            postViewCookie = new Cookie("postView", "[" + post.getId() + "]");
        } else {
            // 3) postView 쿠키가 있으면 => 현재 게시글 ID가 포함되어 있는지 확인
            String value = postViewCookie.getValue();
            String currentPostIdToken = "[" + post.getId() + "]";

            // 쿠키에 현재 게시글 ID가 없다면 => 조회수 증가 + 추가
            if (!value.contains(currentPostIdToken)) {
                post.addViewCount();
                postViewCookie.setValue(value + currentPostIdToken);
            }
        }

        // 4) 쿠키 만료시간(자정까지로 설정)
        //    - '오늘 + 1일 자정' - '현재시간' 을 초 단위로 계산
        LocalDateTime now = LocalDateTime.now();  // 로컬시각
        LocalDateTime midnight = now.toLocalDate().atStartOfDay().plusDays(1); // 오늘 자정 + 1일 = 내일 자정
        long secondsUntilMidnight = Duration.between(now, midnight).getSeconds();

        // 5) 쿠키 설정
        postViewCookie.setPath("/"); // 모든 경로에서 접근 가능
        postViewCookie.setMaxAge((int) secondsUntilMidnight); // 자정까지 남은 시간만큼 유지
        postViewCookie.setHttpOnly(true); // JS에서 쿠키 접근 차단(보안 강화). 필요하면 설정
        // postViewCookie.setSecure(true); // HTTPS 프로토콜에서만 전송(환경에 따라 고려)

        // 6) 최종적으로 response 에 쿠키 추가
        res.addCookie(postViewCookie);
    }

    private void validatePostOfMember(Long memberId, Long authorId) {
        if (!memberId.equals(authorId)) {
            throw new IllegalArgumentException(ErrorCode.COMMENT_NOT_BELONG_TO_MEMBER);
        }
    }
}
