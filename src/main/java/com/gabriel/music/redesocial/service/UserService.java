package com.gabriel.music.redesocial.service;

import com.gabriel.music.redesocial.domain.user.*;
import com.gabriel.music.redesocial.domain.user.DTO.UserInitialRegistration;
import com.gabriel.music.redesocial.domain.user.DTO.UserRegisterToSearchForABand;
import com.gabriel.music.redesocial.domain.user.DTO.UserResponseInitialRegister;
import com.gabriel.music.redesocial.domain.user.DTO.UserResponseRegisterToSearchForABand;
import com.gabriel.music.redesocial.repository.UserRepository;
import com.gabriel.music.redesocial.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageUserService imageUserService;

    public UserResponseInitialRegister initialRegistration(UserInitialRegistration user) throws UserNotFoundException {
        User newUser = modelingNewInitialRegistrationUser(user);
        User responseUser = userRepository.save(newUser);
        return modelingUserResponseInitialRegisterDto(responseUser);
    }

//    public List<UserResponseRegisterToSearchForABand> findAll() {
//        return userRepository.findAll().stream().map(user -> new UserResponseRegisterToSearchForABand(
//                user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getCep(), user.getGoals(),
//                user.getWhatsapp(), user.getAge(), user.getEntryDate(), user.getShows(), user.getGenre(), user.getInstruments(),
//                user.getAvailability(), user.getSocialMedia(), user.getPosts(), user.getImageProfile(), user.getImageBackground(),
//                user.getPhoneNumber(), user.getPhotos(), user.getVideos(), user.getFriends(), user.getPurchasedMaterials(), user.getCreatedMaterials(),
//                user.getSaves(), user.getComments())
//        ).collect(Collectors.toList());
//    }

    public List<UserResponseRegisterToSearchForABand> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapUserToUserResponse)
                .collect(Collectors.toList());
    }

    private UserResponseRegisterToSearchForABand mapUserToUserResponse(User user) {
        return new UserResponseRegisterToSearchForABand(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getCep(),
                user.getGoals(),
                user.getWhatsapp(),
                user.getAge(),
                user.getEntryDate(),
                user.getShows(),
                user.getGenre(),
                user.getInstruments(),
                user.getAvailability(),
                user.getSocialMedia(),
                user.getPosts(),
                user.getImageProfile(),
                user.getImageBackground(),
                user.getPhoneNumber(),
                user.getPhotos(),
                user.getVideos(),
                user.getFriends(),
                user.getPurchasedMaterials(),
                user.getCreatedMaterials(),
                user.getSaves(),
                user.getComments()
        );
    }


    public User findByUsername(String username) throws UserNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(UserNotFoundException::new);
    }

    public User findByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(UserNotFoundException::new);
    }

    public User registrationToSearchForABand(UserRegisterToSearchForABand userRegisterToSearchForABand, String username) throws UserNotFoundException {
        return modelingNewRegistrationToSearchForABand(userRegisterToSearchForABand, username);
    }

    private User modelingNewInitialRegistrationUser(UserInitialRegistration userDTO) {
        var newUser = new User();
        newUser.setUsername(userDTO.username());
        newUser.setEmail(userDTO.email());
        newUser.setPassword(userDTO.password());
        return newUser;
    }

    private User modelingNewRegistrationToSearchForABand(UserRegisterToSearchForABand user, String username) throws UserNotFoundException {
        User existingUser = this.findByUsername(username);
        existingUser.setCep(user.cep());
        existingUser.setAge(user.age());
        existingUser.setShows(user.shows());
        existingUser.setGenre(user.genre());
        existingUser.setInstruments(user.instruments());
        existingUser.setAvailability(user.avaliabity());
        return existingUser;
    }

    private UserResponseInitialRegister modelingUserResponseInitialRegisterDto(User user) {
        return new UserResponseInitialRegister(
                user.getId(), user.getUsername(), user.getEmail()
        );
    }

    public void uploadImageProfileUser(MultipartFile arquivo, String username) throws UserNotFoundException, IOException {
        User user = findByUsername(username);
        imageUserService.saveAndWriteImageProfile(arquivo, user);
    }

    public void uploadBackgroundProfileUser(MultipartFile file, String username) throws UserNotFoundException, IOException {
        User user = findByUsername(username);
        imageUserService.saveAndWriteBackgroundProfile(file, user);

    }
}
