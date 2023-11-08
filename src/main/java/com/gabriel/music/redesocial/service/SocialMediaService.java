package com.gabriel.music.redesocial.service;

import com.gabriel.music.redesocial.domain.user.DTO.SocialMediaUpdate;
import com.gabriel.music.redesocial.domain.user.SocialMedia;
import com.gabriel.music.redesocial.domain.user.DTO.SocialMediaRegistration;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.SocialMediaRepository;
import com.gabriel.music.redesocial.service.exceptions.SocialMediaNotFoundException;
import com.gabriel.music.redesocial.service.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;

@Service
public class SocialMediaService {

    @Autowired
    private SocialMediaRepository socialMediaRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public SocialMedia save(SocialMediaRegistration socialMediaRegistration) throws UserNotFoundException {
        SocialMedia socialMediaForSave = modelingNewSocialMedia(socialMediaRegistration);
        return socialMediaRepository.save(socialMediaForSave);
    }

    private SocialMedia modelingNewSocialMedia(SocialMediaRegistration socialMedia) throws UserNotFoundException {
        User user = userService.findByUsername(socialMedia.user());
        return new SocialMedia(null, socialMedia.socialMedia(), socialMedia.username(), user);
    }

    public List<SocialMedia> findAll() {
        return socialMediaRepository.findAll();
    }

    public SocialMedia findByusernameSocialMedia(String usernameSocialMedia) throws SocialMediaNotFoundException {
        Optional<SocialMedia> listMedias = socialMediaRepository.findByUsernameSocialMedia(usernameSocialMedia);
        return listMedias.orElseThrow(SocialMediaNotFoundException::new);
    }

    @Transactional
    public SocialMedia update(SocialMediaUpdate socialMediaUpdate, String username) throws SocialMediaNotFoundException {
        SocialMedia socialMedia = findByusernameSocialMedia(username);
        socialMedia.setUsernameSocialMedia(socialMediaUpdate.username());
        return socialMediaRepository.save(socialMedia);
    }

    public void deleteByusername(String username) throws SocialMediaNotFoundException {
        SocialMedia socialMedia = findByusernameSocialMedia(username);
        socialMediaRepository.deleteById(socialMedia.getId());
    }
 }
