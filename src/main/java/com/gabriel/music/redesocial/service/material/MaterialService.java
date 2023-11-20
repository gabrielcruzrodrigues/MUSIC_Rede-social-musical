package com.gabriel.music.redesocial.service.material;

import com.gabriel.music.redesocial.domain.enums.Genre;
import com.gabriel.music.redesocial.domain.enums.InstrumentsEnum;
import com.gabriel.music.redesocial.domain.enums.NivelEnum;
import com.gabriel.music.redesocial.domain.material.Material;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.material.MaterialRepository;
import com.gabriel.music.redesocial.service.Exceptions.FileNullContentException;
import com.gabriel.music.redesocial.service.Exceptions.TypeFileErrorException;
import com.gabriel.music.redesocial.service.Exceptions.UserWithoutRequiredInformationException;
import com.gabriel.music.redesocial.service.user.UserService;
import com.gabriel.music.redesocial.service.user.exceptions.UserNotFoundException;
import com.gabriel.music.redesocial.util.MediaFileTypeChecker;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private UserService userService;

    @Value("${files-materials-path}")
    private String filesPath;

    @Transactional
    public Material prepareForSave(String name, String description, Float price, MultipartFile file, InstrumentsEnum instrumentsEnum, Genre genre, NivelEnum nivelEnum, String username) throws UserNotFoundException, IOException, TypeFileErrorException, FileNullContentException, UserWithoutRequiredInformationException {
        Material material = modelingNewMaterialObject(name, description, price, file, instrumentsEnum, genre, nivelEnum, username);
        Material materialWithReferenceFileName = writeFileAndReturnObjectMaterialForSaveInDatabase(material, file);
        return materialRepository.save(materialWithReferenceFileName);
    }

    private Material modelingNewMaterialObject(String name, String description, Float price, MultipartFile file, InstrumentsEnum instrumentsEnum, Genre genre, NivelEnum nivelEnum, String username) throws UserNotFoundException, UserWithoutRequiredInformationException {
        User user = userService.findByUsername(username);
        if (checkingUserInformationToCreateMaterial(user)) {
            return new Material(name, description, price, instrumentsEnum, genre, nivelEnum, user);
        } else {
            throw new UserWithoutRequiredInformationException();
        }
    }

    private boolean checkingUserInformationToCreateMaterial(User user) {
        if (user.getImageProfile() == null || user.getName() == null) {
            return false;
        }
        return true;
    }

    private Material writeFileAndReturnObjectMaterialForSaveInDatabase(Material material, MultipartFile file) throws IOException, FileNullContentException, TypeFileErrorException {
        if (MediaFileTypeChecker.verifyIfIsAFile(file)) {
            byte[] bytes = file.getBytes();
            String newFileName = this.generateNewFileName(file, material);
            Path path = Paths.get(filesPath + "/" + newFileName);
            Files.write(path, bytes);
            material.setReferenceFileName(newFileName);
            material.setSize(calculateFileSize(file));
            return material;
        } else {
            throw new TypeFileErrorException();
        }
    }

    private String calculateFileSize(MultipartFile file) throws IOException {
        long size = file.getBytes().length;
        double sizeInKB = size / 1024.0;
        double sizeInMB = sizeInKB / 1024.0;
        return String.format("%.2f", sizeInMB);
    }

    private String generateNewFileName(MultipartFile file, Material material) {
        String randomId = generateRandomId();
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String newFileName = material.getCreatedMaterialsUserId().getUsername() + "_" + randomId + fileExtension;

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
