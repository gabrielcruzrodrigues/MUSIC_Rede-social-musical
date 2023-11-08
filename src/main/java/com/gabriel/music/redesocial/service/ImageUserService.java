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
import java.util.Objects;
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
                this.deleteCurrentUserImageProfile(user, file);
            } else {
                this.writeFileInDirectory(file, user, "profile");
            }
        }
    }

    public void deleteImageReferenceFromDatabase(String reference) {
        ImageUser imageUser = imageUserRepository.findByImageReference(reference);
        imageUserRepository.deleteById(imageUser.getId());
    }

    private void writeFileInDirectory(MultipartFile file, User user, String option) throws IOException {
        byte[] bytes = file.getBytes();
        String newFileName = generateNewFileName(file, user);
        Path path = Paths.get(pathImages + "\\" + newFileName);
        Files.write(path, bytes);
        saveFileReferenceInDatabase(newFileName, user, option);
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

    private void saveFileReferenceInDatabase(String newFileName, User user, String option) {
        ImageUser imageUser = null;

        if (Objects.equals(option, "profile")) {
            imageUser = new ImageUser(newFileName, user, null, null);
        } else if (Objects.equals(option, "background")) {
            imageUser = new ImageUser(newFileName, null, user, null);
        } else if (Objects.equals(option, "photo")) {
            imageUser = new ImageUser(newFileName, null, null, user);
        }
        imageUserRepository.save(imageUser);
    }

    private void deleteCurrentUserImageProfile(User user, MultipartFile file) throws IOException {
        deleteImageReferenceFromDatabase(user.getImageProfile().getImageReference());
        if (imageUserRepository.findByImageReference(user.getImageProfile().getImageReference()) == null) {
            Boolean verify = deleteFileFromDirectory(user.getImageProfile().getImageReference(), file);
            if (verify) {
                writeFileInDirectory(file, user, "profile");
            }
        }
    }

    private Boolean deleteFileFromDirectory(String referenceForDelete, MultipartFile file) throws IOException {
        Path path = Paths.get(pathImages + "/" + referenceForDelete);
        Files.delete(path);
        return checkIfTheFileExists(referenceForDelete);
    }

    private Boolean checkIfTheFileExists(String referenceForCheck) {
        File file = new File(pathImages, referenceForCheck);
        return file.exists() ? false : true;
    }

    //images backgrounds

    @Transactional
    public void saveAndWriteBackgroundProfile(MultipartFile file, User user) throws IOException {
        if (!file.isEmpty()) {
            if (user.getImageBackground() != null) {
                this.deleteCurrentUserImageBackground(user, file);
            } else {
                this.writeFileInDirectory(file, user, "background");
            }
        }
    }

    private void deleteCurrentUserImageBackground(User user, MultipartFile file) throws IOException {
        deleteImageReferenceFromDatabase(user.getImageBackground().getImageReference());
        if (imageUserRepository.findByImageReference(user.getImageBackground().getImageReference()) == null) {
            Boolean verify = deleteFileFromDirectory(user.getImageBackground().getImageReference(), file);
            if (verify) {
                writeFileInDirectory(file, user, "background");
            }
        }
    }

    // photos

    @Transactional
    public void saveAndWritePhotoUser(MultipartFile file, User user) throws IOException {
        if (!file.isEmpty()) {
                this.writeFileInDirectory(file, user, "photo");
        }
    }

    public void deleteById(Long id) {
        imageUserRepository.deleteById(id);
    }
}
