package com.gabriel.music.redesocial.domain.user.DTO;

import com.gabriel.music.redesocial.domain.enums.SocialMediaEnum;

public record SocialMediaRegistrationDTO(SocialMediaEnum socialMedia, String username, String user) {
}
