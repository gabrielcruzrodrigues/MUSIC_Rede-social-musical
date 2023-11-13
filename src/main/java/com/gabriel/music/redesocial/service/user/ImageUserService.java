package com.gabriel.music.redesocial.service.user;

import com.gabriel.music.redesocial.domain.user.ImageUser;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.ImageUserRepository;
import com.gabriel.music.redesocial.service.exceptions.ErrorDeleteFileException;
import com.gabriel.music.redesocial.service.exceptions.FileNotFoundException;
import com.gabriel.music.redesocial.service.exceptions.TypeFileErrorException;
import com.gabriel.music.redesocial.util.MediaFileTypeChecker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.jcodec.api.JCodecException;
import org.jcodec.common.Demuxer;
import org.jcodec.common.DemuxerTrack;
import org.jcodec.common.io.NIOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    public void deleteImageReferenceFromDatabase(String reference) throws FileNotFoundException {
        ImageUser imageUser = this.findImageByFilename(reference);
        imageUserRepository.deleteById(imageUser.getId());
    }

    private void writeFileInDirectory(MultipartFile file, User user, String option) throws IOException {
        byte[] bytes = file.getBytes();
        String newFileName = generateNewFileName(file, user);
        Path path = Paths.get(pathImages + "/" + newFileName);
        Files.write(path, bytes);
        saveFileReferenceInDatabase(newFileName, user, option);
    }

    private String generateNewFileName(MultipartFile file, User user) {
        String randomId = generateRandomId();
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String newFileName = user.getUsername() + "_" + randomId + fileExtension;

        if (imageUserRepository.findByImageReference(newFileName).isEmpty()) {
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

    private ImageUser findImageByFilename(String filename) throws FileNotFoundException {
        Optional<ImageUser> imageUser = imageUserRepository.findByImageReference(filename);
        return imageUser.orElseThrow(FileNotFoundException::new);
    }

    private Boolean deleteFileInDirectory(String referenceForDelete) throws IOException {
        Path path = Paths.get(pathImages + "/" + referenceForDelete);
        Files.delete(path);
        return checkIfTheFileExists(referenceForDelete);
    }

    //images profile

    public void deleteImageUser(String filename) throws FileNotFoundException, IOException, ErrorDeleteFileException {
        deleteImageReferenceFromDatabase(filename);
        if (imageUserRepository.findByImageReference(filename).isEmpty()) {
            deleteFileInDirectory(filename);
        } else {
            throw new ErrorDeleteFileException();
        }
    }

    @Transactional
    public void saveAndWriteImageProfile(MultipartFile file, User user) throws IOException, FileNotFoundException, TypeFileErrorException {
        if (MediaFileTypeChecker.verifyIfIsAPhoto(file)) {
            if (user.getImageProfile() != null) {
                this.deleteCurrentUserImageProfile(user, file);
            } else {
                this.writeFileInDirectory(file, user, "profile");
            }
        } else {
            throw new TypeFileErrorException();
        }
    }

    private void deleteCurrentUserImageProfile(User user, MultipartFile file) throws IOException, FileNotFoundException {
        deleteImageReferenceFromDatabase(user.getImageProfile().getImageReference());
        if (imageUserRepository.findByImageReference(user.getImageProfile().getImageReference()).isEmpty()) {
            Boolean verify = deleteFileInDirectory(user.getImageProfile().getImageReference());
            if (verify) {
                writeFileInDirectory(file, user, "profile");
            }
        }
    }

    private Boolean checkIfTheFileExists(String referenceForCheck) {
        File file = new File(pathImages, referenceForCheck);
        return file.exists() ? false : true;
    }

    //images backgrounds

    @Transactional
    public void saveAndWriteBackgroundProfile(MultipartFile file, User user) throws IOException, FileNotFoundException, TypeFileErrorException {
        if (MediaFileTypeChecker.verifyIfIsAPhoto(file)) {
            if (user.getImageBackground() != null) {
                this.deleteCurrentUserImageBackground(user, file);
            } else {
                this.writeFileInDirectory(file, user, "background");
            }
        } else {
            throw new TypeFileErrorException();
        }
    }

    private void deleteCurrentUserImageBackground(User user, MultipartFile file) throws IOException, FileNotFoundException {
        deleteImageReferenceFromDatabase(user.getImageBackground().getImageReference());
        if (imageUserRepository.findByImageReference(user.getImageBackground().getImageReference()).isEmpty()) {
            Boolean verify = deleteFileInDirectory(user.getImageBackground().getImageReference());
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
