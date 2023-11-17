package com.gabriel.music.redesocial.service.post;

import com.gabriel.music.redesocial.domain.post.ImagePost;
import com.gabriel.music.redesocial.repository.post.ImagePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagePostService {

    @Value("${images-post-path}")
    private String pathImagesPost;

    @Autowired
    private ImagePostRepository imagePostRepository;

    public List<ImagePost> findAll() {
        return imagePostRepository.findAll();
    }


}
