package com.gabriel.music.redesocial.repository.post;

import com.gabriel.music.redesocial.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByCodec(String codec);
}
