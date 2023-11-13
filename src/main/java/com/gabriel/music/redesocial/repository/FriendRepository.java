package com.gabriel.music.redesocial.repository;

import com.gabriel.music.redesocial.domain.user.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
}
