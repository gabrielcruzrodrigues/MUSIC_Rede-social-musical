package com.gabriel.music.redesocial.service.post;

import com.gabriel.music.redesocial.domain.post.Post;
import com.gabriel.music.redesocial.repository.post.PostRepository;
import com.gabriel.music.redesocial.service.post.exceptions.PostNotFoundException;
import com.gabriel.music.redesocial.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ImagePostService imagePostService;

    public void save(String title, String description, String creator, List<MultipartFile> images, List<MultipartFile> videos) {
        Post post = criationNewPostNoMedias(title, description, creator);
        if (post.getCodec() != null) {
            Boolean verify = saveMidiasInPost(post, images, videos);
        }
    }

    public Post findByCodec(String codec) throws PostNotFoundException {
        Optional<Post> post = postRepository.findByCodec(codec);
        return post.orElseThrow(PostNotFoundException::new);
    }
}
