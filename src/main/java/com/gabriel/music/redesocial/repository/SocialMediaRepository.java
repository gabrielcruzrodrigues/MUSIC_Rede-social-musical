package com.gabriel.music.redesocial.repository;

import com.gabriel.music.redesocial.domain.user.SocialMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialMediaRepository extends JpaRepository<SocialMedia, Long> {
    Optional<SocialMedia> findByUsernameSocialMedia(String usernameSocialMedia);
}
