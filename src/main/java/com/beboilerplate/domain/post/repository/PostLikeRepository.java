package com.beboilerplate.domain.post.repository;

import com.beboilerplate.domain.member.entity.Member;
import com.beboilerplate.domain.post.entity.Post;
import com.beboilerplate.domain.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    void deleteByMemberAndPost(Member member, Post post);
}
