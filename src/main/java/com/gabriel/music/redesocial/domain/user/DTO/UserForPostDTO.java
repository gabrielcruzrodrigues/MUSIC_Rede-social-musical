package com.gabriel.music.redesocial.domain.user.DTO;

import com.gabriel.music.redesocial.domain.user.ImageUser;

public record UserForPostDTO(
        Long id,
        String name,
        String username,
        ImageUser imageProfile,
        ImageUser imageBackground
) {
}
