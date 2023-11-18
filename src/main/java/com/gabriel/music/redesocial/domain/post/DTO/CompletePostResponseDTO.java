package com.gabriel.music.redesocial.domain.post.DTO;

import com.gabriel.music.redesocial.domain.post.Comment;
import com.gabriel.music.redesocial.domain.post.ImagePost;
import com.gabriel.music.redesocial.domain.post.VideoPost;
import com.gabriel.music.redesocial.domain.user.DTO.UserForPostDTO;
import com.gabriel.music.redesocial.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

public record CompletePostResponseDTO(
        Long id,
        String codec,
        String title,
        String description,
        LocalDateTime createDate,
        Long likes,
        Integer shares,
        UserForPostDTO user,
        List<ImagePost> images,
        List<VideoPost> videos,
        List<Comment> comments
) {
}
