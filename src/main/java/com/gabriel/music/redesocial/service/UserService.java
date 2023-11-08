package com.gabriel.music.redesocial.service;

import com.gabriel.music.redesocial.domain.user.*;
import com.gabriel.music.redesocial.domain.user.DTO.*;
import com.gabriel.music.redesocial.repository.UserRepository;
import com.gabriel.music.redesocial.service.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
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
    public UserResponseInitialRegisterDTO initialRegistration(UserInitialRegistrationDTO user) throws UserNotFoundException {
        User newUser = modelingNewInitialRegistrationUser(user);
        User responseUser = userRepository.save(newUser);
        return modelingUserResponseInitialRegisterDto(responseUser);
    }

    @Transactional
    public UserResponseRegisterToSearchForABandDTO registrationToSearchForABand(UserRegisterToSearchForABandDTO userRegisterToSearchForABandDTO) throws UserNotFoundException {
        User newUser = modelingNewRegistrationToSearchForABand(userRegisterToSearchForABandDTO);
        userRepository.save(newUser);
        return transformUserToUserResponseRegisterToSearchForABandDTO(newUser);
    }

    public User findByUsername(String username) throws UserNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(UserNotFoundException::new);
    }

//    public User findByEmail(String email) throws UserNotFoundException {
//        Optional<User> user = userRepository.findByEmail(email);
//        return user.orElseThrow(UserNotFoundException::new);
//    }

    public List<UserResponseRegisterToSearchForABandDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::transformUserToUserResponseRegisterToSearchForABandDTO)
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

    private UserResponseRegisterToSearchForABandDTO transformUserToUserResponseRegisterToSearchForABandDTO(User user) {
        return new UserResponseRegisterToSearchForABandDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getCep(),
                user.getAbout(),
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

    private User modelingNewInitialRegistrationUser(UserInitialRegistrationDTO userDTO) {
        var newUser = new User();
        newUser.setUsername(userDTO.username());
        newUser.setEmail(userDTO.email());
        newUser.setPassword(userDTO.password());
        newUser.setEntryDate(LocalDate.now());
        return newUser;
    }

    private User modelingNewRegistrationToSearchForABand(UserRegisterToSearchForABandDTO user) throws UserNotFoundException {
        User existingUser = this.findByUsername(user.username());
        existingUser.setName(user.name());
        existingUser.setCep(user.cep());
        existingUser.setAge(user.age());
        existingUser.setShows(user.shows());
        existingUser.setGenre(user.genre());
        existingUser.setInstruments(user.instruments());
        existingUser.setAvailability(user.avaliabity());
        return existingUser;
    }

    private UserResponseInitialRegisterDTO modelingUserResponseInitialRegisterDto(User user) {
        return new UserResponseInitialRegisterDTO(
                user.getId(), user.getUsername(), user.getEmail()
        );
    }

    @Transactional
    public void updateAbout(AboutUpdateDTO aboutUpdateDTO, String username) throws UserNotFoundException {
        User user = findByUsername(username);
        user.setAbout(aboutUpdateDTO.about());
        userRepository.save(user);
    }

    @Transactional
    public void updateWhatsapp(WhatsAppUpdateDTO whatsAppUpdateDTO, String username) throws UserNotFoundException {
        User user = findByUsername(username);
        user.setWhatsapp(whatsAppUpdateDTO.whatsapp());
        userRepository.save(user);
    }
}
