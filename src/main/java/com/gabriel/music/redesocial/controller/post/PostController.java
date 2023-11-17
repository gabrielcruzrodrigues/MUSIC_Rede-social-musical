package com.gabriel.music.redesocial.controller.post;

import com.gabriel.music.redesocial.domain.post.Post;
import com.gabriel.music.redesocial.service.user.exceptions.UserNotFoundException;
import com.gabriel.music.redesocial.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {

    @Autowired
    private PostService postService;

//    @GetMapping("/all")
//    public ResponseEntity<List<Post>> findAll() {
//        return ResponseEntity.ok().body(postService.findAll());
//    }

    @PostMapping("/create")
    public ResponseEntity<Post> create(@RequestParam("title") String title, @RequestParam("description") String description,
                                       @RequestParam("creator") String creator, @RequestParam("image") List<MultipartFile> images, @RequestParam("video") List<MultipartFile> videos) throws UserNotFoundException {
        postService.save(title, description, creator, images, videos);
        return ResponseEntity.ok().build();
    }
}
