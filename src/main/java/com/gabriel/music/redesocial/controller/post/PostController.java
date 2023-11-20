package com.gabriel.music.redesocial.controller.post;

import com.gabriel.music.redesocial.domain.post.Comment;
import com.gabriel.music.redesocial.domain.post.DTO.CompletePostResponseDTO;
import com.gabriel.music.redesocial.domain.post.Post;
import com.gabriel.music.redesocial.service.Exceptions.ErrorDeleteException;
import com.gabriel.music.redesocial.service.Exceptions.FileNullContentException;
import com.gabriel.music.redesocial.service.Exceptions.TypeFileErrorException;
import com.gabriel.music.redesocial.service.post.CommentService;
import com.gabriel.music.redesocial.service.post.PostService;
import com.gabriel.music.redesocial.service.post.exceptions.CommentNotFoundException;
import com.gabriel.music.redesocial.service.post.exceptions.PostNotFoundException;
import com.gabriel.music.redesocial.service.user.exceptions.UserNotFoundException;
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

    @Autowired
    private CommentService commentService;

    @GetMapping("/all")
    public ResponseEntity<List<Post>> findAll() {
        return ResponseEntity.ok().body(postService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Post> create(@RequestParam("title") String title,
                                       @RequestParam("description") String description,
                                       @RequestParam("creator") String creator,
                                       @RequestParam(value = "image", required = false) List<MultipartFile> images,
                                       @RequestParam(value = "video", required = false) List<MultipartFile> videos) throws UserNotFoundException,
                                        FileNullContentException, TypeFileErrorException, IOException {
        postService.save(title, description, creator, images, videos);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/{codec}")
    public ResponseEntity<CompletePostResponseDTO> findPostByCodec(@PathVariable String codec) throws PostNotFoundException, UserNotFoundException {
        return ResponseEntity.ok().body(postService.findCompletePostByCodec(codec));
    }

    @PutMapping("/add-like/{codec}")
    public ResponseEntity<Object> addLike(@PathVariable String codec) throws PostNotFoundException {
        postService.addLike(codec);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/remove-like/{codec}")
    public ResponseEntity<Object> removeLike(@PathVariable String codec) throws PostNotFoundException {
        postService.removeLike(codec);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/toshare/{codec}")
    public ResponseEntity<String> toShare(@PathVariable String codec) throws PostNotFoundException {
        return ResponseEntity.ok().body(postService.sharedPost(codec));
    }

    //comments

    @PostMapping("/comment")
    public ResponseEntity<Comment> newComment(@RequestParam("comment") String comment, @RequestParam("username") String username,
                                              @RequestParam("codec") String codec) throws UserNotFoundException, PostNotFoundException {
        return ResponseEntity.ok().body(commentService.save(comment, username, codec));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long id) throws CommentNotFoundException, ErrorDeleteException {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/comment/find-all")
    public ResponseEntity<List<Comment>> findAllComments() {
        return ResponseEntity.ok().body(commentService.findAll());
    }

    @PutMapping("/comment/add-like/{id}")
    public ResponseEntity<Object> addLikeToComment(@PathVariable Long id) throws CommentNotFoundException {
        commentService.addLikeInComment(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/comment/remove-like/{id}")
    public ResponseEntity<Object> removeLikeToComment(@PathVariable Long id) throws CommentNotFoundException {
        commentService.removeLikeInComment(id);
        return ResponseEntity.noContent().build();
    }

}
