package com.gabriel.music.redesocial.service.user;

import com.gabriel.music.redesocial.domain.user.Friend;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.user.FriendRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class FriendServiceTest {

    public static final String NAME = "gabriel";
    public static final String IMAGE_PROFILE = "jalsdfulasdf.img";
    @InjectMocks
    private FriendService friendService;

    @Mock
    private FriendRepository friendRepository;

    private Friend friend;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startFriend();
    }

    @Test
    void mustReturnANewFriend_whenToCallSave() {
        Mockito.when(friendRepository.save(any())).thenReturn(friend);
        Friend response = friendService.save(user, user);
        assertNotNull(response);
        assertEquals(Friend.class, response.getClass());
        assertEquals(NAME, response.getUsername());
        assertEquals(IMAGE_PROFILE, response.getImageProfile());
        assertEquals(user, response.getUser());
    }

    @Test
    void mustReturnNewObjectFriend_whenToCallModelingNewObjectFriend() {
        Friend response = friendService.modelingNewObjectFriend(user, user);
        assertNotNull(response);
    }

    void startFriend() {
        user = new User();
        friend = new Friend(NAME, IMAGE_PROFILE, user);
    }
}