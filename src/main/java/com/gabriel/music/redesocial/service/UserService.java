package com.gabriel.music.redesocial.service;

import com.gabriel.music.redesocial.domain.user.*;
import com.gabriel.music.redesocial.domain.user.DTO.UserInitialRegistration;
import com.gabriel.music.redesocial.domain.user.DTO.UserRegisterToSearchForABand;
import com.gabriel.music.redesocial.domain.user.DTO.UserResponseInitialRegister;
import com.gabriel.music.redesocial.domain.user.DTO.UserResponseRegisterToSearchForABand;
import com.gabriel.music.redesocial.repository.UserRepository;
import com.gabriel.music.redesocial.service.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
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

    @Transactional
    public UserResponseInitialRegister initialRegistration(UserInitialRegistration user) throws UserNotFoundException {
        User newUser = modelingNewInitialRegistrationUser(user);
        User responseUser = userRepository.save(newUser);
        return modelingUserResponseInitialRegisterDto(responseUser);
    }

    @Transactional
    public User registrationToSearchForABand(UserRegisterToSearchForABand userRegisterToSearchForABand) throws UserNotFoundException {
        User newUser = modelingNewRegistrationToSearchForABand(userRegisterToSearchForABand);
        return userRepository.save(newUser);
    }

    public User findByUsername(String username) throws UserNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(UserNotFoundException::new);
    }

    public User findByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(UserNotFoundException::new);
    }

    public List<UserResponseRegisterToSearchForABand> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapUserToUserResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void uploadImageProfileUser(MultipartFile file, String username) throws UserNotFoundException, IOException {
        User user = findByUsername(username);
        imageUserService.saveAndWriteImageProfile(file, user);
    }

    @Transactional
    public void uploadBackgroundProfileUser(MultipartFile file, String username) throws UserNotFoundException, IOException {
        User user = findByUsername(username);
        imageUserService.saveAndWriteBackgroundProfile(file, user);
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

    private User modelingNewInitialRegistrationUser(UserInitialRegistration userDTO) {
        var newUser = new User();
        newUser.setUsername(userDTO.username());
        newUser.setEmail(userDTO.email());
        newUser.setPassword(userDTO.password());
        return newUser;
    }

    private User modelingNewRegistrationToSearchForABand(UserRegisterToSearchForABand user) throws UserNotFoundException {
        User existingUser = this.findByUsername(user.username());
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
}
