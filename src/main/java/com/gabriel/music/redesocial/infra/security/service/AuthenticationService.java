package com.gabriel.music.redesocial.infra.security.service;

import com.gabriel.music.redesocial.infra.security.domain.authentication.DTO.AuthenticationDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public Authentication login(AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        return this.authenticationManager.authenticate(usernamePassword);
    }
}
