package com.gabriel.music.redesocial.repository.post;

import com.gabriel.music.redesocial.domain.post.ImagePost;
import com.gabriel.music.redesocial.domain.post.VideoPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoPostRepository extends JpaRepository<VideoPost, Long> {
    Optional<VideoPost> findByVideoReference(String filename);
}
