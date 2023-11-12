package com.gabriel.music.redesocial.controller;

import com.gabriel.music.redesocial.domain.user.DTO.*;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.service.user.ImageUserService;
import com.gabriel.music.redesocial.service.user.UserService;
import com.gabriel.music.redesocial.service.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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
    public ResponseEntity<List<UserResponseRegisterToSearchForABandDTO>> findAll() {
        return ResponseEntity.ok().body(userService.findAllUsers());
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) throws UserNotFoundException {
        return ResponseEntity.ok().body(userService.findByUsername(username));
    }

    @PostMapping("/initial-registration")
    public ResponseEntity<UserResponseInitialRegisterDTO> saveInitialUserRegistration(@RequestBody @Valid UserInitialRegistrationDTO userInitialRegistrationDTO) throws UserNotFoundException {
        UserResponseInitialRegisterDTO user = userService.initialRegistration(userInitialRegistrationDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(user.username())
                .toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PostMapping("/registration-search-a-band")
    public ResponseEntity<UserResponseRegisterToSearchForABandDTO> saveRegisterSearchABand(@RequestBody @Valid UserRegisterToSearchForABandDTO userRegisterToSearchForABandDTO) throws UserNotFoundException {
        UserResponseRegisterToSearchForABandDTO user = userService.registrationToSearchForABand(userRegisterToSearchForABandDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(user.username())
                .toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PostMapping("/update-image-profile")
    public ResponseEntity<Object> updateImageProfile(@RequestParam("file") MultipartFile file,
                                                     @RequestParam("username") String username) throws UserNotFoundException, IOException {
        userService.uploadImageProfileUser(file, username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-background-profile")
    public ResponseEntity<Object> updateBackgroundImage(@RequestParam("file") MultipartFile file,
                                                        @RequestParam("username") String username) throws UserNotFoundException, IOException {
        userService.uploadBackgroundProfileUser(file, username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload-photo")
    public ResponseEntity<Object> updatePhotoUser(@RequestParam("file") MultipartFile file,
                                                        @RequestParam("username") String username) throws UserNotFoundException, IOException {
        userService.uploadPhotoUser(file, username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload-video")
    public ResponseEntity<Object> updateVideUser(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("username") String username) throws UserNotFoundException, IOException {
        userService.uploadVideoUser(file, username);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/about/{username}")
    public ResponseEntity<Object> updateAbout(@PathVariable String username, @RequestBody AboutUpdateDTO about) throws UserNotFoundException {
        userService.updateAbout(about, username);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/whatsapp/{username}")
    public ResponseEntity<Object> updateWhatsapp(@PathVariable String username, @RequestBody WhatsAppUpdateDTO whatsAppUpdateDTO) throws UserNotFoundException {
        userService.updateWhatsapp(whatsAppUpdateDTO, username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/phone-number")
    public ResponseEntity<Object> updatePhoneNumber(@RequestBody PhoneNumberRegistrationDTO phoneNumberRegistrationDTO) throws UserNotFoundException {
        userService.registerPhoneNumber(phoneNumberRegistrationDTO);
        return ResponseEntity.ok().build();
    }
}
