package com.gabriel.music.redesocial.service.post;

import com.gabriel.music.redesocial.domain.post.DTO.PostCreation;
import com.gabriel.music.redesocial.domain.post.Post;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.post.PostRepository;
import com.gabriel.music.redesocial.service.exceptions.UserNotFoundException;
import com.gabriel.music.redesocial.service.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Post save(PostCreation postCreation) throws UserNotFoundException {
        Post post = modelingNewPost(postCreation);
        return postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    private Post modelingNewPost(PostCreation postCreation) throws UserNotFoundException {
        User user = userService.findByUsername(postCreation.creator());
        return new Post(postCreation.title(), postCreation.description(), user);
    }
}
