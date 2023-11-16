package com.gabriel.music.redesocial.controller.user;

import com.gabriel.music.redesocial.domain.user.DTO.SocialMediaUpdateDTO;
import com.gabriel.music.redesocial.domain.user.SocialMedia;
import com.gabriel.music.redesocial.domain.user.DTO.SocialMediaRegistrationDTO;
import com.gabriel.music.redesocial.service.user.SocialMediaService;
import com.gabriel.music.redesocial.service.exceptions.SocialMediaNotFoundException;
import com.gabriel.music.redesocial.service.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("social-media")
public class SocialMediaController {

    @Autowired
    private SocialMediaService socialMediaService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @PostMapping("/create")
    public ResponseEntity<SocialMedia> save(@RequestBody @Valid SocialMediaRegistrationDTO socialMediaRegistrationDTO) throws UserNotFoundException {
        SocialMedia socialMedia = socialMediaService.save(socialMediaRegistrationDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{username}")
                .buildAndExpand(socialMedia.getUsernameSocialMedia())
                .toUri();
        return ResponseEntity.created(uri).body(socialMedia);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<SocialMedia>> findAll() {
        return ResponseEntity.ok().body(socialMediaService.findAll());
    }

    @PutMapping("/update/{usernameSocial}")
    public ResponseEntity<SocialMedia> update(@RequestBody SocialMediaUpdateDTO socialMediaUpdateDTO, @PathVariable String usernameSocial) throws SocialMediaNotFoundException {
        return ResponseEntity.ok().body(socialMediaService.update(socialMediaUpdateDTO, usernameSocial));
    }

    @DeleteMapping("/delete/{usernameSocialMedia}")
    public ResponseEntity<Object> deleteByUsername(@PathVariable String usernameSocialMedia) throws SocialMediaNotFoundException {
        socialMediaService.deleteByusername(usernameSocialMedia);
        return ResponseEntity.ok().build();
    }
}
