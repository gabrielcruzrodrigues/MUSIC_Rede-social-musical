package com.gabriel.music.redesocial.service.post;

import com.gabriel.music.redesocial.domain.post.Post;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.post.PostRepository;
import com.gabriel.music.redesocial.service.Exceptions.FileNullContentException;
import com.gabriel.music.redesocial.service.Exceptions.TypeFileErrorException;
import com.gabriel.music.redesocial.service.post.exceptions.PostNotFoundException;
import com.gabriel.music.redesocial.service.user.UserService;
import com.gabriel.music.redesocial.service.user.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Value("${host-path}")
    private String hostPath;
    private String findByCodecUrlEndPoint = "/post/search/";

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ImagePostService imagePostService;

    @Autowired
    private VideoPostService videoPostService;


    public void save(String title, String description, String creator, List<MultipartFile> images, List<MultipartFile> videos) throws UserNotFoundException, FileNullContentException, TypeFileErrorException, IOException {
        Post post = creationNewPostNoMedias(title, description, creator);
        Post postSaved = postRepository.save(post);
        if (postSaved.getCodec() != null) {
            saveMediasInPost(postSaved, images, videos);
        }
    }

    private void saveMediasInPost(Post post, List<MultipartFile> images, List<MultipartFile> videos) throws FileNullContentException, TypeFileErrorException, IOException {
        if (images != null) {
            boolean verify = imagePostService.saveAndWriteImage(post, images);
            if (verify && videos != null) {
                videoPostService.saveAndWriteVideo(post, videos);
            }
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

    public List<Post> findAll() {
        return postRepository.findAll();
    }

//    public void deleteByCodec(String codec) throws PostNotFoundException {
//        Post post = this.findByCodec(codec);
//        postRepository.delete(post);
//    }

    @Transactional
    public void addLike(String codec) throws PostNotFoundException {
        Post post = this.findByCodec(codec);
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
    }

    @Transactional
    public void removeLike(String codec) throws PostNotFoundException {
        Post post = this.findByCodec(codec);
        post.setLikes(post.getLikes() - 1);
        postRepository.save(post);
    }

    public String sharedPost(String codec) throws PostNotFoundException {
        Post post = this.findByCodec(codec);
        post.setShares(post.getShares() + 1);
        Post postSaved = postRepository.save(post);
        return createLinkForToSharePost(postSaved.getCodec());
    }

    private String createLinkForToSharePost(String codec) {
        return hostPath + findByCodecUrlEndPoint + codec;
    }
}
