package com.gabriel.music.redesocial.service.user;

import com.gabriel.music.redesocial.domain.user.DTO.PhoneNumberRegistrationDTO;
import com.gabriel.music.redesocial.domain.user.PhoneNumber;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.user.PhoneNumberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberService {

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Transactional
    public PhoneNumber save(PhoneNumberRegistrationDTO phoneNumberRegistrationDTO, User user) {
        PhoneNumber phoneNumber = modelingNewPhoneNumber(phoneNumberRegistrationDTO, user);
        return phoneNumberRepository.save(phoneNumber);
    }

    private PhoneNumber modelingNewPhoneNumber(PhoneNumberRegistrationDTO phoneNumberRegistrationDTO, User user) {
        return new PhoneNumber(null, phoneNumberRegistrationDTO.number(), user);
    }
}
