package com.gabriel.music.redesocial.service.user;

import com.gabriel.music.redesocial.domain.user.DTO.SocialMediaUpdateDTO;
import com.gabriel.music.redesocial.domain.user.SocialMedia;
import com.gabriel.music.redesocial.domain.user.DTO.SocialMediaRegistrationDTO;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.user.SocialMediaRepository;
import com.gabriel.music.redesocial.service.Exceptions.SocialMediaNotFoundException;
import com.gabriel.music.redesocial.service.user.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocialMediaService {

    @Autowired
    private SocialMediaRepository socialMediaRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public SocialMedia save(SocialMediaRegistrationDTO socialMediaRegistrationDTO) throws UserNotFoundException {
        SocialMedia socialMediaForSave = modelingNewSocialMedia(socialMediaRegistrationDTO);
        return socialMediaRepository.save(socialMediaForSave);
    }

    private SocialMedia modelingNewSocialMedia(SocialMediaRegistrationDTO socialMedia) throws UserNotFoundException {
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
    public SocialMedia update(SocialMediaUpdateDTO socialMediaUpdateDTO, String username) throws SocialMediaNotFoundException {
        SocialMedia socialMedia = findByusernameSocialMedia(username);
        socialMedia.setUsernameSocialMedia(socialMediaUpdateDTO.username());
        return socialMediaRepository.save(socialMedia);
    }

    public void deleteByusername(String username) throws SocialMediaNotFoundException {
        SocialMedia socialMedia = findByusernameSocialMedia(username);
        socialMediaRepository.deleteById(socialMedia.getId());
    }
 }
