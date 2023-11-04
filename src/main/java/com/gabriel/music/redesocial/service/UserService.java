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

    public User InitialRegistration(UserInitialRegistration user) throws UserNotFoundException {
        this.findByUsername(user.username());
        User newUser = createNewInitialUser(user);
        return userRepository.save(newUser);
    }


    public User findByUsername(String username) throws UserNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new UserNotFoundException());
    }

    public User findByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new UserNotFoundException())
    }

    public User registrationToSearchForABand(UserRegisterToSearchForABand userRegisterToSearchForABand) {
        return null;
    }

    private User createNewInitialUser(UserInitialRegistration userDTO) {
        var newUser = new User();
        newUser.setUsername(userDTO.username());
        newUser.setEmail(userDTO.email());
        newUser.setPassword(userDTO.password());
        return newUser;
    }
}
