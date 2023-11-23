package com.gabriel.music.redesocial.domain.user.DTO;

import com.gabriel.music.redesocial.infra.security.domain.authentication.UserRole;

public record UserInitialRegistrationDTO(String username, String email, String password, UserRole role) {
}
