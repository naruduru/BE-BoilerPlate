package com.beboilerplate.domain.post.service;

import com.beboilerplate.domain.member.entity.Member;
import com.beboilerplate.domain.post.dto.request.CommentRequest;
import com.beboilerplate.domain.post.dto.response.CommentResponse;
import com.beboilerplate.domain.post.entity.Comment;
import com.beboilerplate.domain.post.entity.Post;
import com.beboilerplate.domain.post.repository.CommentRepository;
import com.beboilerplate.domain.post.repository.PostRepository;
import com.beboilerplate.global.exception.EntityNotFoundException;
import com.beboilerplate.global.exception.IllegalArgumentException;
import com.beboilerplate.global.response.ErrorCode;
import com.beboilerplate.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AuthUtil authUtil;

    @Transactional
    public CommentResponse addComment(Long postId, CommentRequest commentRequest) {
        Member loginMember = authUtil.getLoginMember();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));
        Comment createdComment = Comment.builder()
                .author(loginMember)
                .post(post)
                .content(commentRequest.getContent())
                .build();

        return CommentResponse.from(commentRepository.save(createdComment));
    }

    @Transactional
    public CommentResponse addReply(Long postId, Long parentId, CommentRequest commentRequest) {
        Member loginMember = authUtil.getLoginMember();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));
        Comment parent = commentRepository.findById(parentId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        Comment createdComment = Comment.builder()
                .author(loginMember)
                .post(post)
                .parent(parent)
                .content(commentRequest.getContent())
                .build();

        return CommentResponse.from(commentRepository.save(createdComment));
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));
        List<Comment> comments = commentRepository.findAllByPostAndParentIsNullOrderByCreatedAtDesc(post);

        return comments.stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponse updateComment(Long postId, Long commentId, CommentRequest commentRequest) {
        Member loginMember = authUtil.getLoginMember();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
        validateCommentOfMember(loginMember.getId(), comment.getAuthor().getId());
        validateCommentOfPost(post.getId(), comment.getPost().getId());
        comment.update(commentRequest.getContent());

        return CommentResponse.from(comment);
    }


    @Transactional
    public CommentResponse deleteComment(Long postId, Long commentId) {
        Member loginMember = authUtil.getLoginMember();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
        validateCommentOfMember(loginMember.getId(), comment.getAuthor().getId());
        validateCommentOfPost(post.getId(), comment.getPost().getId());
        comment.softDelete();

        return CommentResponse.from(comment);
    }

    private void validateCommentOfMember(Long memberId, Long commenterId) {
        if (!memberId.equals(commenterId)) {
            throw new IllegalArgumentException(ErrorCode.COMMENT_NOT_BELONG_TO_MEMBER);
        }
    }

    private void validateCommentOfPost(Long postId, Long postOfCommentId) {
        if (!postId.equals(postOfCommentId)) {
            throw new IllegalArgumentException(ErrorCode.COMMENT_NOT_BELONG_TO_POST);
        }
    }
}
