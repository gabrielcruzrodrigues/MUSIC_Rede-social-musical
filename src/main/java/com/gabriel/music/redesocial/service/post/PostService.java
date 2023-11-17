package com.gabriel.music.redesocial.service.post;

import com.gabriel.music.redesocial.domain.post.ImagePost;
import com.gabriel.music.redesocial.domain.post.Post;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.post.PostRepository;
import com.gabriel.music.redesocial.service.post.exceptions.PostNotFoundException;
import com.gabriel.music.redesocial.service.user.UserService;
import com.gabriel.music.redesocial.service.user.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ImagePostService imagePostService;

    @Autowired
    private VideoPostService videoPostService;

    public void save(String title, String description, String creator, List<MultipartFile> images, List<MultipartFile> videos) throws UserNotFoundException {
        Post post = creationNewPostNoMedias(title, description, creator);
        if (post.getCodec() != null) {
            saveMediasInPost(post, images, videos);
        }
    }

    private void saveMediasInPost(Post post, List<MultipartFile> images, List<MultipartFile> videos) {
        ImagePost imagePost = imagePostService.saveAndWriteImage(post, images);
        if (Objects.equals(imagePost.getPost().getCodec(), post.getCodec())) {
            videoPostService.saveAndWriteVideo(post, videos);
        }
    }

    private Post creationNewPostNoMedias(String title, String description, String creator) throws UserNotFoundException {
        User user = userService.findByUsername(creator);
        return new Post(title, description, user);
    }

    public Post findByCodec(String codec) throws PostNotFoundException {
        Optional<Post> post = postRepository.findByCodec(codec);
        return post.orElseThrow(PostNotFoundException::new);
    }
}
