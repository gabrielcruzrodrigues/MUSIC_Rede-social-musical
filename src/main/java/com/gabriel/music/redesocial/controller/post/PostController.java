package com.gabriel.music.redesocial.controller.post;

import com.gabriel.music.redesocial.domain.post.Post;
import com.gabriel.music.redesocial.service.Exceptions.FileNullContentException;
import com.gabriel.music.redesocial.service.Exceptions.TypeFileErrorException;
import com.gabriel.music.redesocial.service.post.exceptions.PostNotFoundException;
import com.gabriel.music.redesocial.service.user.exceptions.UserNotFoundException;
import com.gabriel.music.redesocial.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/all")
    public ResponseEntity<List<Post>> findAll() {
        return ResponseEntity.ok().body(postService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Post> create(@RequestParam("title") String title,
                                       @RequestParam("description") String description,
                                       @RequestParam("creator") String creator,
                                       @RequestParam(value = "image", required = false) List<MultipartFile> images,
                                       @RequestParam(value = "video", required = false) List<MultipartFile> videos) throws UserNotFoundException, FileNullContentException, TypeFileErrorException, IOException {
        postService.save(title, description, creator, images, videos);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/{codec}")
    public ResponseEntity<Post> findByCodec(@PathVariable String codec) throws PostNotFoundException {
        return ResponseEntity.ok().body(postService.findByCodec(codec));
    }

//    @DeleteMapping("/delete/{codec}")
//    public ResponseEntity<Object> delete(@PathVariable String codec) throws PostNotFoundException {
//        postService.deleteByCodec(codec);
//        return ResponseEntity.noContent().build();
//    }

}
