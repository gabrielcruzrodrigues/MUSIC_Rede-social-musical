package com.gabriel.music.redesocial.service;

import com.gabriel.music.redesocial.domain.user.ImageUser;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.ImageUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class ImageUserService {
    private final String pathImages = "C:/Users/conta/OneDrive/Documentos/images-user";

    @Autowired
    private ImageUserRepository imageUserRepository;

    public List<ImageUser> findAll() {
        return imageUserRepository.findAll();
    }

    @Transactional
    public void saveAndWriteToDirectory(MultipartFile file, User user) throws IOException {
        try {
            if (!file.isEmpty()) {
                log.info(file.getContentType());
                byte[] bytes = file.getBytes();
                Path path = Paths.get(pathImages + "\\" + user.getUsername() + file.getOriginalFilename());
                Files.write(path, bytes);
            }

            ImageUser imageUser = new ImageUser(
                    user.getUsername() + file.getOriginalFilename(), null, null, user
            );
            imageUserRepository.save(imageUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
