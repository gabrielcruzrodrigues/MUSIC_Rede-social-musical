package com.gabriel.music.redesocial.controller;

import com.gabriel.music.redesocial.domain.user.DTO.UserInitialRegistration;
import com.gabriel.music.redesocial.domain.user.DTO.UserResponseInitialRegister;
import com.gabriel.music.redesocial.domain.user.DTO.UserResponseRegisterToSearchForABand;
import com.gabriel.music.redesocial.service.ImageUserService;
import com.gabriel.music.redesocial.service.UserService;
import com.gabriel.music.redesocial.service.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageUserService imageUserService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserResponseRegisterToSearchForABand>> findAll() {
        return ResponseEntity.ok().body(userService.findAllUsers());
    }

    @PostMapping("/initialRegistration")
    public ResponseEntity<UserResponseInitialRegister> saveInitialUserRegistration(@RequestBody @Valid UserInitialRegistration userInitialRegistration) throws UserNotFoundException {
        UserResponseInitialRegister user = userService.initialRegistration(userInitialRegistration);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(user.username())
                .toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PostMapping("/updateImageProfile")
    public ResponseEntity updateImageProfile(@RequestParam("file") MultipartFile arquivo,
                                             @RequestParam("username") String username) throws UserNotFoundException, IOException {
        userService.uploadImageProfileUser(arquivo, username);
        return ResponseEntity.ok().build();
    }
}
