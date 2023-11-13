package com.gabriel.music.redesocial.repository;

import com.gabriel.music.redesocial.domain.user.ImageUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageUserRepository extends JpaRepository<ImageUser, Long> {
//    ImageUser findByImageReference(String imageReference);
    Optional<ImageUser> findByImageReference(String imageReference);
}
