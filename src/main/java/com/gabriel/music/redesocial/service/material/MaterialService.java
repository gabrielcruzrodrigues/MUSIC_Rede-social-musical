package com.gabriel.music.redesocial.service.material;

import com.gabriel.music.redesocial.domain.enums.GenreEnum;
import com.gabriel.music.redesocial.domain.enums.InstrumentsEnum;
import com.gabriel.music.redesocial.domain.enums.NivelEnum;
import com.gabriel.music.redesocial.domain.material.Material;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.material.MaterialRepository;
import com.gabriel.music.redesocial.service.Exceptions.FileNullContentException;
import com.gabriel.music.redesocial.service.Exceptions.TypeFileErrorException;
import com.gabriel.music.redesocial.service.user.UserService;
import com.gabriel.music.redesocial.service.user.exceptions.UserNotFoundException;
import com.gabriel.music.redesocial.util.MediaFileTypeChecker;
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
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private UserService userService;

    @Value("${files-materials-path}")
    private String filesPath;

    public Material prepareForSave(String name, String description, Float price, MultipartFile file, InstrumentsEnum instrumentsEnum, GenreEnum genreEnum, NivelEnum nivelEnum, String username) throws UserNotFoundException, IOException, TypeFileErrorException, FileNullContentException {
        Material material = modelingNewMaterialObject(name, description, price, file, instrumentsEnum, genreEnum, nivelEnum, username);
        Material materialWithReferenceFileName = saveAndWriteFile(material, file);
        if (materialWithReferenceFileName != null) {
            return materialRepository.save(materialWithReferenceFileName);
        } else {
            throw new TypeFileErrorException();
        }
    }

    private Material modelingNewMaterialObject(String name, String description, Float price, MultipartFile file, InstrumentsEnum instrumentsEnum, GenreEnum genreEnum, NivelEnum nivelEnum, String username) throws UserNotFoundException {
        User user = userService.findByUsername(username);
        return new Material(name, description, price, instrumentsEnum, genreEnum, nivelEnum, user);
    }

    private Material saveAndWriteFile(Material material, MultipartFile file) throws IOException, FileNullContentException {
        if (!MediaFileTypeChecker.verigyIfIsAFile(file)) {
            byte[] bytes = file.getBytes();
            String newFileName = this.generateNewFileName(file, material);
            Path path = Paths.get(filesPath + "/" + newFileName);
            Files.write(path, bytes);
            material.setReferenceFileName(newFileName);
            return material;
        }
        return null;
    }

    private String generateNewFileName(MultipartFile file, Material material) {
        String randomId = generateRandomId();
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String newFileName = material.getCreatedMaterialsUser_id().getUsername() + "_" + randomId + fileExtension;

        if (materialRepository.findByReferenceFileName(newFileName).isEmpty()) {
            return newFileName;
        } else {
            return newFileName + UUID.randomUUID().toString().substring(0, 5);
        }
    }

    private String generateRandomId() {
        return UUID.randomUUID().toString().substring(0, 30);
    }
}
