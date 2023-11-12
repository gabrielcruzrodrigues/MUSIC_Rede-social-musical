package com.gabriel.music.redesocial.repository;

import com.gabriel.music.redesocial.domain.user.VideoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoUserRepository extends JpaRepository<VideoUser, Long> {
    VideoUser findByVideoReference(String newFileName);
}
