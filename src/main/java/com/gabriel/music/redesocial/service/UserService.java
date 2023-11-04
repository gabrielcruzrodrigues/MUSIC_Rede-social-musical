package com.gabriel.music.redesocial.service;

import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.domain.user.UserInitialRegistration;
import com.gabriel.music.redesocial.domain.user.UserRegisterToSearchForABand;
import com.gabriel.music.redesocial.repository.UserRepository;
import com.gabriel.music.redesocial.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User initialRegistration(UserInitialRegistration user) throws UserNotFoundException {
        User newUser = modelingNewInitialUser(user);
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

    public User registrationToSearchForABand(UserRegisterToSearchForABand userRegisterToSearchForABand, String username) throws UserNotFoundException {
        return modelingNewRegistrationToSearchForABand(userRegisterToSearchForABand, username);
    }

    private User modelingNewInitialUser(UserInitialRegistration userDTO) {
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
}
