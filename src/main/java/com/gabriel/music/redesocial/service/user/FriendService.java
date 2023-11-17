package com.gabriel.music.redesocial.service.user;

import com.gabriel.music.redesocial.domain.user.Friend;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.user.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

    public Friend save(User user, User userFriend) {
        Friend friend = modelingNewObjectFriend(user, userFriend);
        return friendRepository.save(friend);
    }

    public Friend modelingNewObjectFriend(User user, User userFriend) {
        String imageProfile;
        if (userFriend.getImageProfile() == null) {
            imageProfile = null;
        } else {
            imageProfile = userFriend.getImageProfile().getImageReference();
        }
        return new Friend(userFriend.getUsername(), imageProfile, user);
    }
}
