package com.gabriel.music.redesocial.service;

import com.gabriel.music.redesocial.domain.user.ImageUser;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.ImageUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class ImageUserService {
    private final String pathImages = "C:/Users/conta/OneDrive/Documentos/images-user";

    @Autowired
    private ImageUserRepository imageUserRepository;

    public void saveAndWriteToDirectory(MultipartFile arquivo, User user) throws IOException {
        try {
            if (!arquivo.isEmpty()) {
                log.info(arquivo.getContentType());
                byte[] bytes = arquivo.getBytes();
                Path caminho = Paths.get(pathImages + "\\" + user.getUsername() + arquivo.getOriginalFilename());
                Files.write(caminho, bytes);
            }

            ImageUser imageUser = new ImageUser(
                    user.getUsername() + arquivo.getOriginalFilename(), null, null, user
            );
            imageUserRepository.save(imageUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
