package com.gabriel.music.redesocial.domain.user.DTO;

import com.gabriel.music.redesocial.domain.enums.SocialMediaEnum;
import com.gabriel.music.redesocial.domain.user.User;

public record SocialMediaRegistration(SocialMediaEnum socialMedia, String username, String user) {
}
