package com.gabriel.music.redesocial.controller;

import com.gabriel.music.redesocial.domain.user.DTO.*;
import com.gabriel.music.redesocial.domain.user.Friend;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.service.exceptions.ErrorDeleteFileException;
import com.gabriel.music.redesocial.service.exceptions.FileNotFoundException;
import com.gabriel.music.redesocial.service.exceptions.UserMidiaNotFoundException;
import com.gabriel.music.redesocial.service.user.ImageUserService;
import com.gabriel.music.redesocial.service.user.UserService;
import com.gabriel.music.redesocial.service.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    //search

    @GetMapping("/findAll")
    public ResponseEntity<List<UserResponseRegisterToSearchForABandDTO>> findAll() {
        return ResponseEntity.ok().body(userService.findAllUsers());
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) throws UserNotFoundException {
        return ResponseEntity.ok().body(userService.findByUsername(username));
    }

    //registration

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

    //midias

    @PostMapping("/update-image-profile")
    public ResponseEntity<Object> updateImageProfile(@RequestParam("file") MultipartFile file,
                                                     @RequestParam("username") String username) throws UserNotFoundException, IOException, FileNotFoundException {
        userService.uploadImageProfileUser(file, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/media/image-profile/{username}")
    public ResponseEntity<Resource> getImageProfile(@PathVariable String username) throws UserNotFoundException, FileNotFoundException, UserMidiaNotFoundException {
        Resource imageProfile = userService.getImageProfile(username);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + imageProfile.getFilename())
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageProfile);
    }

    @DeleteMapping("/media/image-profile/delete/{filename}")
    public ResponseEntity<Object> deleteImageProfile(@PathVariable String filename) throws FileNotFoundException, IOException, ErrorDeleteFileException {
        imageUserService.deleteImageUser(filename);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-background-profile")
    public ResponseEntity<Object> updateBackgroundImage(@RequestParam("file") MultipartFile file,
                                                        @RequestParam("username") String username) throws UserNotFoundException, IOException, FileNotFoundException {
        userService.uploadBackgroundProfileUser(file, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/media/image-background/{username}")
    public ResponseEntity<Resource> getBackgroundProfile(@PathVariable String username) throws UserNotFoundException, FileNotFoundException, UserMidiaNotFoundException {
        Resource backgroundProfile = userService.getBackgroundProfile(username);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + backgroundProfile.getFilename())
                .contentType(MediaType.IMAGE_JPEG)
                .body(backgroundProfile);
    }

    @DeleteMapping("/media/background-profile/delete/{filename}")
    public ResponseEntity<Object> deleteBackgroundProfile(@PathVariable String filename) throws FileNotFoundException, IOException, ErrorDeleteFileException {
        imageUserService.deleteImageUser(filename);
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

    //uploads

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

    //friends

    @PostMapping("/new-friend")
    public ResponseEntity<Friend> addNewFriend(@RequestBody @Valid FriendRegistrationDTO friendRegistrationDTO) throws UserNotFoundException {
        Friend friend = userService.addFriend(friendRegistrationDTO.username(), friendRegistrationDTO.friend());
        return ResponseEntity.ok().body(friend);
    }
}
