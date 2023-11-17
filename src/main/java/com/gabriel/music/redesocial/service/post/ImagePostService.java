package com.gabriel.music.redesocial.service.post;

import com.gabriel.music.redesocial.domain.post.ImagePost;
import com.gabriel.music.redesocial.domain.post.Post;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.post.ImagePostRepository;
import com.gabriel.music.redesocial.service.Exceptions.FileNullContentException;
import com.gabriel.music.redesocial.service.Exceptions.TypeFileErrorException;
import com.gabriel.music.redesocial.util.MediaFileTypeChecker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ImagePostService {

    @Value("${images-post-path}")
    private String pathImagesPost;

    @Autowired
    private ImagePostRepository imagePostRepository;

    public List<ImagePost> findAll() {
        return imagePostRepository.findAll();
    }

    @Transactional
    public boolean saveAndWriteImage(Post post, List<MultipartFile> images) throws FileNullContentException, TypeFileErrorException, IOException {
        for (MultipartFile file : images) {
            if (file == null) {
                throw new FileNullContentException();
            } else if(!MediaFileTypeChecker.verifyIfIsAPhoto(file)) {
                throw new TypeFileErrorException();
            }
        }
        return true;
    }

    private void writeFileInDirectory(List<MultipartFile> images, Post post) throws IOException {
        for (MultipartFile image : images) {
            byte[] bytes = image.getBytes();
            String newFileName = this.generateNewFileName(image, post);
            Path path = Paths.get(pathImagesPost + "/" + newFileName);
            Files.write(path, bytes);
            this.saveFileReferenceInDatabase(newFileName, post);
        }
    }

    private void saveFileReferenceInDatabase(String newFileName, Post post) {
        ImagePost imagePost = new ImagePost(newFileName, post);
        imagePostRepository.save(imagePost);
    }

    private String generateNewFileName(MultipartFile image, Post post) {
        String randomId = generateRandomId();
        String originalFileName = image.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String newFileName = post.getCodec() + "_" + randomId + fileExtension;

        if (imagePostRepository.findByImageReference(newFileName).isEmpty()) {
            return newFileName;
        } else {
            return newFileName + UUID.randomUUID().toString().substring(0, 5);
        }
    }

    private String generateRandomId() {
        return UUID.randomUUID().toString().substring(0, 30);
    }
}
