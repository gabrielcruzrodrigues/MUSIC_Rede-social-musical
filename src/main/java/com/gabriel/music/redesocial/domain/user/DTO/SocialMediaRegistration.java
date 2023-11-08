package com.gabriel.music.redesocial.domain.user.DTO;

import com.gabriel.music.redesocial.domain.enums.SocialMediaEnum;
import com.gabriel.music.redesocial.domain.user.User;

public record SocialMediaRegistration(Long id, SocialMediaEnum socialMediaEnum, String username, String user) {
}
