package com.gabriel.music.redesocial.controller.material;

import com.gabriel.music.redesocial.domain.enums.GenreEnum;
import com.gabriel.music.redesocial.domain.enums.InstrumentsEnum;
import com.gabriel.music.redesocial.domain.enums.NivelEnum;
import com.gabriel.music.redesocial.domain.material.Material;
import com.gabriel.music.redesocial.service.Exceptions.FileNullContentException;
import com.gabriel.music.redesocial.service.Exceptions.TypeFileErrorException;
import com.gabriel.music.redesocial.service.material.MaterialService;
import com.gabriel.music.redesocial.service.user.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @PostMapping("/create")
    public ResponseEntity<Material> createNewMaterial(@RequestParam("name") String name,
                                                      @RequestParam("description") String description,
                                                      @RequestParam("price") Float price,
                                                      @RequestParam("file")MultipartFile file,
                                                      @RequestParam(value = "instruments", required = false) InstrumentsEnum instrumentsEnum,
                                                      @RequestParam(value = "genres", required = false) GenreEnum genreEnum,
                                                      @RequestParam("nivel") NivelEnum nivelEnum,
                                                      @RequestParam("creatorUsername") String username) throws UserNotFoundException, IOException, TypeFileErrorException, FileNullContentException {
        Material material = materialService.prepareForSave(name, description, price, file, instrumentsEnum, genreEnum, nivelEnum, username);
        return ResponseEntity.ok().body(material);
    }
}
