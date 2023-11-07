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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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

    @Transactional
    public void saveAndWriteImageProfile(MultipartFile file, User user) throws IOException {
        if (!file.isEmpty()) {
            if (user.getImageProfile() != null) {
                this.deleteCurrentUserImage(user, file);
            } else {
                this.writeFileInDirectory(file, user);
            }
        }
    }

    @Transactional
    public void saveAndWriteBackgroundProfile(MultipartFile file, User user) throws IOException {
//        try {
//            if (!file.isEmpty()) {
//                if (user.getImageBackground() != null) {
//                    this.
//                } else {
//                    this.writeAndSaveFile(file, user);
//                }
//                byte[] bytes = file.getBytes();
//                Path path = Paths.get(pathImages + "\\" + user.getUsername() + file.getOriginalFilename());
//                Files.write(path, bytes);
//            }
//
//            ImageUser imageUser = new ImageUser(user.getUsername() + file.getOriginalFilename(), null, user, null);
//            imageUserRepository.save(imageUser);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void deleteImageReferenceFromDatabase(User user) {
        ImageUser imageUser = imageUserRepository.findByImageReference(user.getImageProfile().getImageReference());
        imageUserRepository.deleteById(imageUser.getId());
    }

//    private void writeFileInDirectory(MultipartFile file, User user) throws IOException {
//        byte[] bytes = file.getBytes();
//        Path path = Paths.get(pathImages + "\\" + user.getUsername() + file.getOriginalFilename());
//
//        Files.write(path, bytes);
//        saveFileReferenceInDatabase(file, user);
//    }

    private void writeFileInDirectory(MultipartFile file, User user) throws IOException {
        byte[] bytes = file.getBytes();
        String newFileName = generateNewFileName(file, user);
        Path path = Paths.get(pathImages + "\\" + newFileName);
        Files.write(path, bytes);
        saveFileReferenceInDatabase(newFileName, user);
    }

    private String generateNewFileName(MultipartFile file, User user) {
        String randomId = generateRandomId();
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String newFileName = user.getUsername() + "_" + randomId + fileExtension;

        if (imageUserRepository.findByImageReference(newFileName) == null) {
            return newFileName;
        } else {
            return newFileName + UUID.randomUUID().toString().substring(0, 5);
        }
    }

    private String generateRandomId() {
        return UUID.randomUUID().toString().substring(0, 30);
    }

    private void saveFileReferenceInDatabase(String newFileName, User user) {
        ImageUser imageUser = new ImageUser(newFileName, user, null, null);
        imageUserRepository.save(imageUser);
    }

    private void deleteCurrentUserImage(User user, MultipartFile file) throws IOException {
        deleteImageReferenceFromDatabase(user);
        if (imageUserRepository.findByImageReference(user.getImageProfile().getImageReference()) == null) {
            Boolean verify = deleteFileFromDirectory(user, file);
            if (verify) {
                writeFileInDirectory(file, user);
            }
        }
    }

    private Boolean deleteFileFromDirectory(User user, MultipartFile file) throws IOException {
        Path path = Paths.get(pathImages + "/" + user.getImageProfile().getImageReference());
        Files.delete(path);
        return checkIfTheFileExists(user);
    }

    private Boolean checkIfTheFileExists(User user) {
        File file = new File(pathImages, user.getImageProfile().getImageReference());
        return file.exists() ? false : true;
    }

    public void deleteById(Long id) {
        imageUserRepository.deleteById(id);
    }
}
