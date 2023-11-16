package com.gabriel.music.redesocial.controller.post;

import com.gabriel.music.redesocial.domain.post.DTO.PostCreation;
import com.gabriel.music.redesocial.domain.post.Post;
import com.gabriel.music.redesocial.service.exceptions.UserNotFoundException;
import com.gabriel.music.redesocial.service.post.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Post> create(@RequestBody @Valid PostCreation postCreation) throws UserNotFoundException {
        Post post = postService.save(postCreation);
        return ResponseEntity.ok().body(post);
    }
}
