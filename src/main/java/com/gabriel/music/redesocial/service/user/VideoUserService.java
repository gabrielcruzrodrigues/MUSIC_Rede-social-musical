package com.gabriel.music.redesocial.service.user;

import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.domain.user.VideoUser;
import com.gabriel.music.redesocial.repository.user.VideoUserRepository;
import com.gabriel.music.redesocial.service.user.exceptions.FileNullContentException;
import com.gabriel.music.redesocial.service.user.exceptions.TypeFileErrorException;
import com.gabriel.music.redesocial.util.MediaFileTypeChecker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class VideoUserService {

    @Value("${videos-user-path}")
    private String pathVideos;

    @Autowired
    private VideoUserRepository videoUserRepository;

    public void saveAndWriteVideoProfile(MultipartFile file, User user) throws IOException, TypeFileErrorException, FileNullContentException {
        if (MediaFileTypeChecker.verifyIfIsAVideo(file)) {
            this.writeVideoInDirectory(file, user);
        } else {
            throw new TypeFileErrorException();
        }
    }

    private void writeVideoInDirectory(MultipartFile file, User user) throws IOException {
        byte[] bytes = file.getBytes();
        String newFileName = generateNewFileName(file, user);
        Path path = Paths.get(pathVideos + "/" + newFileName);
        Files.write(path, bytes);
        saveFileReferenceInDataBase(newFileName, user);
    }

    @Transactional
    private void saveFileReferenceInDataBase(String newFileName, User user) {
        VideoUser videoUser = new VideoUser(newFileName, user);
        videoUserRepository.save(videoUser);
    }

    private String generateNewFileName(MultipartFile file, User user) {
        String randomId = generateRandomId();
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String newFileName = user.getUsername() + "_" + randomId + fileExtension;

        if (videoUserRepository.findByVideoReference(newFileName) == null) {
            return newFileName;
        } else {
            return newFileName + UUID.randomUUID().toString().substring(0, 5);
        }
    }

    private String generateRandomId() {
        return UUID.randomUUID().toString().substring(0, 30);
    }
}
