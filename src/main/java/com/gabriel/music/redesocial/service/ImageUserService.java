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
//        String imageProfileReference = createImageProfileReference(user);

        if (!file.isEmpty()) {
            if (user.getImageProfile() != null) {
                log.info("entrou no delete primeiro");
                this.deleteImageOfCurrentUser(user, file);
            } else {
                this.writeAndSaveFile(file, user);
            }
        }
    }

    private void writeAndSaveFile(MultipartFile file, User user) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(pathImages + "\\" + user.getUsername() + file.getOriginalFilename());
        Files.write(path, bytes);

        ImageUser imageUser = new ImageUser(
                user.getUsername() + file.getOriginalFilename(), user, null, null
        );
        imageUserRepository.save(imageUser);
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

//    private String createImageProfileReference(User user) {
//        ImageUser imageProfile = user.getImageProfile();
//        if (imageProfile == null) {
//            return null;
//        } else {
//            String imageReference = imageProfile.getImageReference();
//            return imageReference;
//        }
//    }

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

    private void deleteImageOfCurrentUser(User user, MultipartFile file) throws IOException {
        log.info("=============passou===============");
        //            deleteImageReferenceToDatabase(user.getImageProfile().getImageReference());

        ImageUser imageUser = imageUserRepository.findByImageReference(user.getImageProfile().getImageReference());
        imageUserRepository.deleteById(imageUser.getId());


        deleteFile(user);
//        writeAndSaveFile(file, user);
        log.info("=============final===============");
    }

    private void deleteFile(User user) throws IOException {
        Path path = Paths.get(pathImages + "/" + user.getImageProfile().getImageReference());
        Files.delete(path);
    }

//    public void deleteImageReferenceToDatabase(String imageReference) {
//        ImageUser imageUser = imageUserRepository.findByImageReference(imageReference);
//        log.info("----------------------- chamou metodo delete");
//        imageUserRepository.deleteById(imageUser.getId());
//    }

    public void delete(Long id) {
        log.info("================== Valor enviado para o delete: " + id + " " + id.getClass());
        imageUserRepository.deleteById(id);
        log.info("----------------------- deletou id: " + id);
    }
}
