package com.beboilerplate.domain.post.repository;

import com.beboilerplate.domain.post.entity.Comment;
import com.beboilerplate.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostAndParentIsNullOrderByCreatedAtDesc(Post post);
}
