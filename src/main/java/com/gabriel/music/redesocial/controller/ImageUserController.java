package com.gabriel.music.redesocial.controller;

import com.gabriel.music.redesocial.domain.user.ImageUser;
import com.gabriel.music.redesocial.service.ImageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("image-user")
public class ImageUserController {

    @Autowired
    private ImageUserService imageUserService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ImageUser>> getAll() {
        return ResponseEntity.ok().body(imageUserService.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        imageUserService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
