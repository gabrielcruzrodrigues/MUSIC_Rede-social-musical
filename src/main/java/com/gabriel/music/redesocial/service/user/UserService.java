package com.gabriel.music.redesocial.service.user;

import com.gabriel.music.redesocial.domain.user.*;
import com.gabriel.music.redesocial.domain.user.DTO.*;
import com.gabriel.music.redesocial.repository.user.UserRepository;
import com.gabriel.music.redesocial.service.exceptions.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageUserService imageUserService;

    @Autowired
    private VideoUserService videoUserService;

    @Autowired
    private PhoneNumberService phoneNumberService;

    @Autowired
    private FriendService friendService;

    @Value("${images-user-path}")
    private String pathImages;

    public UserService() {}

    //registration

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

    //search

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

    //midias

    @Transactional
    public void uploadImageProfileUser(MultipartFile file, String username) throws UserNotFoundException, IOException, FileNotFoundException, TypeFileErrorException, FileNullContentException {
        User user = findByUsername(username);
        imageUserService.saveAndWriteImageProfile(file, user);
    }

    @Transactional
    public void uploadBackgroundProfileUser(MultipartFile file, String username) throws UserNotFoundException, IOException, FileNotFoundException, TypeFileErrorException, FileNullContentException {
        User user = findByUsername(username);
        imageUserService.saveAndWriteBackgroundProfile(file, user);
    }

    @Transactional
    public void uploadPhotoUser(MultipartFile file, String username) throws UserNotFoundException, IOException, FileNullContentException, TypeFileErrorException {
        User user = findByUsername(username);
        imageUserService.saveAndWritePhotoUser(file, user);
    }

    @Transactional
    public void uploadVideoUser(MultipartFile file, String username) throws UserNotFoundException, IOException, TypeFileErrorException, FileNullContentException {
        User user = findByUsername(username);
        videoUserService.saveAndWriteVideoProfile(file, user);
    }

    public Resource getImageProfile(String username) throws UserNotFoundException, FileNotFoundException, UserMidiaNotFoundException {
        User user = findByUsername(username);
        if (user.getImageProfile().getImageReference() != null) {
            Resource resource = new FileSystemResource(pathImages + "/" + user.getImageProfile().getImageReference());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException();
            }
        } else {
            throw new UserMidiaNotFoundException();
        }
    }

    public Resource getBackgroundProfile(String username) throws UserNotFoundException, FileNotFoundException, UserMidiaNotFoundException {
        User user = findByUsername(username);
        if (user.getImageBackground().getImageReference() != null) {
            Resource resource = new FileSystemResource(pathImages + "/" + user.getImageBackground().getImageReference());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException();
            }
        } else {
            throw new UserMidiaNotFoundException();
        }
    }

    //updates

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

    @Transactional
    public void registerPhoneNumber(PhoneNumberRegistrationDTO phoneNumberRegistrationDTO) throws UserNotFoundException {
        User user = findByUsername(phoneNumberRegistrationDTO.username());
        phoneNumberService.save(phoneNumberRegistrationDTO, user);
    }

   //friends

    public Friend addFriend(String username, String friendUsername) throws UserNotFoundException {
        User user = findByUsername(username);
        User userFriend = findByUsername(friendUsername);
        return friendService.save(user, userFriend);
    }

//    public List<Resource> getAllPhotosUser(String username) throws UserNotFoundException, java.io.FileNotFoundException {
//        User user = this.findByUsername(username);
//        if (user.getPhotos() != null) {
//            return imageUserService.convertReferenceInFiles(user.getPhotos());
//        } else {
//            throw new RuntimeException();
//        }
//    }
}
