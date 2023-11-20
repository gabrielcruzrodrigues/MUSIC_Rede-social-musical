package com.gabriel.music.redesocial.controller.material;

import com.gabriel.music.redesocial.domain.enums.Genre;
import com.gabriel.music.redesocial.domain.enums.InstrumentsEnum;
import com.gabriel.music.redesocial.domain.enums.NivelEnum;
import com.gabriel.music.redesocial.domain.material.Material;
import com.gabriel.music.redesocial.service.Exceptions.*;
import com.gabriel.music.redesocial.service.material.MaterialService;
import com.gabriel.music.redesocial.service.user.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
                                                      @RequestParam(value = "instrument", required = false) InstrumentsEnum instrument,
                                                      @RequestParam(value = "genre", required = false) Genre genre,
                                                      @RequestParam("nivel") NivelEnum nivelEnum,
                                                      @RequestParam("creatorUsername") String username) throws UserNotFoundException, IOException, TypeFileErrorException, FileNullContentException, UserWithoutRequiredInformationException {
        Material material = materialService.prepareForSave(name, description, price, file, instrument, genre, nivelEnum, username);
        return ResponseEntity.ok().body(material);
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<Material>> findAll() {
        return ResponseEntity.ok().body(this.materialService.findAll());
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<Material>> findByName(@PathVariable String name) throws FileNotFoundException {
        return ResponseEntity.ok().body(this.materialService.findByName(name));
    }

    @GetMapping("/search/codec/{codec}")
    public ResponseEntity<Material> findByCodec(@PathVariable String codec) throws FileNotFoundException {
        return ResponseEntity.ok().body(this.materialService.findByCodec(codec));
    }

    @DeleteMapping("/delete/{codec}")
    public ResponseEntity<Object> delete(@PathVariable String codec) throws FileNotFoundException, ErrorDeleteFileException, ErrorDeleteException {
        this.materialService.deleteMaterial(codec);
        return ResponseEntity.noContent().build();
    }
}
