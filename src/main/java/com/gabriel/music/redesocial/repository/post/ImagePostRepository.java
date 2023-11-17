package com.gabriel.music.redesocial.repository.post;

import com.gabriel.music.redesocial.domain.post.ImagePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImagePostRepository extends JpaRepository<ImagePost, Long> {
    Optional<ImagePost> findByImageReference(String filename);
}
