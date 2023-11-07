package com.gabriel.music.redesocial.service;

import com.gabriel.music.redesocial.domain.user.ImageUser;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.ImageUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageUserService {

    @Value("${images-user-path}")
    private String pathImages;

    @Autowired
    private ImageUserRepository imageUserRepository;

    public List<ImageUser> findAll() {
        return imageUserRepository.findAll();
    }

    public void saveAndWriteImageProfile(MultipartFile file, User user) throws IOException {
        String imageProfileReference = createImageProfileReference(user);
        try {
            if (!file.isEmpty()) {
                if (imageProfileReference != null) {
                    deleteImageOfCurrentUser(user, imageProfileReference);
                } else {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(pathImages + "\\" + user.getUsername() + file.getOriginalFilename());
                    Files.write(path, bytes);
                };
            }

            ImageUser imageUser = new ImageUser(
                    user.getUsername() + file.getOriginalFilename(), user, null, null
            );
            imageUserRepository.save(imageUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void saveAndWriteImageProfile(MultipartFile file, User user) throws IOException {
//        String imageProfileReference = createImageProfileReference(user);
//        try {
//            if (!file.isEmpty()) {
//                if (imageProfileReference != null) {
//                    deleteImageOfCurrentUser(user, imageProfileReference);
//                } else {
//                    byte[] bytes = file.getBytes();
//                    Path path = Paths.get(pathImages + "\\" + user.getUsername() + file.getOriginalFilename());
//                    Files.write(path, bytes);
//                };
//            }
//
//            ImageUser imageUser = new ImageUser(
//                    user.getUsername() + file.getOriginalFilename(), user, null, null
//            );
//            imageUserRepository.save(imageUser);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private String createImageProfileReference(User user) {
        ImageUser imageProfile = user.getImageProfile();
        if (imageProfile == null) {
            return null;
        } else {
            String imageReference = imageProfile.getImageReference();
            return imageReference;
        }
    }

    @Transactional
    public void saveAndWriteBackgroundProfile(MultipartFile file, User user) throws IOException {
        try {
            if (!file.isEmpty()) {
                log.info(file.getContentType());
                byte[] bytes = file.getBytes();
                Path path = Paths.get(pathImages + "\\" + user.getUsername() + file.getOriginalFilename());
                Files.write(path, bytes);
            }

            ImageUser imageUser = new ImageUser(
                    user.getUsername() + file.getOriginalFilename(), null, user, null
            );
            imageUserRepository.save(imageUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteImageOfCurrentUser(User user, String imageProfileReference) {
        deleteImageReferenceToDatabase(imageProfileReference);

//        try {
//            Path path = Paths.get(pathImages + "/" + imageProfileReference);
//            log.info("----------- nome do path: " + path);
//            Files.delete(path);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void deleteImageReferenceToDatabase(String imageReference) {
        ImageUser imageUser = imageUserRepository.findByImageReference(imageReference);
        this.delete(imageUser.getId());
        log.info("----------------------- deletou id: " + imageUser.getId());
    }

    public void delete(Long id) {
        imageUserRepository.deleteById(id);
    }
}
