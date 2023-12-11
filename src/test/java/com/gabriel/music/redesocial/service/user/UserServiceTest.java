package com.gabriel.music.redesocial.service.user;

import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    public static final String NAME = "Gabriel";
    public static final String EMAIL = "gabriel@gmail.com";
    public static final String PASSWORD = "1234567890";
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUsers();
    }

    @Test
    void mustRegistrationAndReturnAUserResponseInitialRegisterDTO_whenToCallInitialRegistration() {

    }

    @Test
    void registrationToSearchForABand() {
    }

    @Test
    void getToken() {
    }

    @Test
    void findByUsernameForServer() {
    }

    @Test
    void findByUsernameForResponseClient() {
    }

    @Test
    void findAllUsers() {
    }

    @Test
    void uploadImageProfileUser() {
    }

    @Test
    void uploadBackgroundProfileUser() {
    }

    @Test
    void uploadPhotoUser() {
    }

    @Test
    void uploadVideoUser() {
    }

    @Test
    void getImageProfile() {
    }

    @Test
    void getBackgroundProfile() {
    }

    @Test
    void updateAbout() {
    }

    @Test
    void updateWhatsapp() {
    }

    @Test
    void registerPhoneNumber() {
    }

    @Test
    void addFriend() {
    }

    void startUsers() {
        user = new User();
        user.setUsername(NAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
    }
}