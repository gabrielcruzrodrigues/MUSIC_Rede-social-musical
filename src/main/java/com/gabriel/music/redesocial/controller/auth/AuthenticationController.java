package com.gabriel.music.redesocial.controller.auth;

import com.gabriel.music.redesocial.domain.user.DTO.UserInitialRegistrationDTO;
import com.gabriel.music.redesocial.domain.user.DTO.UserResponseInitialRegisterDTO;
import com.gabriel.music.redesocial.service.user.UserService;
import com.gabriel.music.redesocial.service.user.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

//    @Autowired
//    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

//    @PostMapping("/login")
//    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authenticationDTO) throws GenerateTokenErrorException {
//        Authentication auth = this.authenticationService.login(authenticationDTO);
//        return ResponseEntity.ok(new LoginResponseDTO(this.userService.getToken(auth)));
//    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseInitialRegisterDTO> saveInitialUserRegistration(@RequestBody @Valid UserInitialRegistrationDTO userInitialRegistrationDTO) throws UserNotFoundException {
        UserResponseInitialRegisterDTO user = userService.initialRegistration(userInitialRegistrationDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(user.username())
                .toUri();
        return ResponseEntity.created(uri).body(user);
    }
}
