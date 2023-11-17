package com.gabriel.music.redesocial.service.post;

import com.gabriel.music.redesocial.domain.post.Post;
import com.gabriel.music.redesocial.domain.post.VideoPost;
import com.gabriel.music.redesocial.repository.post.VideoPostRepository;
import com.gabriel.music.redesocial.service.Exceptions.FileNullContentException;
import com.gabriel.music.redesocial.service.Exceptions.TypeFileErrorException;
import com.gabriel.music.redesocial.util.MediaFileTypeChecker;
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
public class VideoPostService {

    @Value("${videos-post-path}")
    private String pathVideosPost;

    @Autowired
    private VideoPostRepository videoPostRepository;

    public List<VideoPost> findAll() {
        return videoPostRepository.findAll();
    }

    public void saveAndWriteVideo(Post post, List<MultipartFile> videos) throws FileNullContentException, TypeFileErrorException, IOException {
        for (MultipartFile file : videos) {
            if (file == null) {
                throw new FileNullContentException();
            } else if(!MediaFileTypeChecker.verifyIfIsAVideo(file)) {
                throw new TypeFileErrorException();
            }
        }
        this.writeFileInDirectory(videos, post);
    }

    private void writeFileInDirectory(List<MultipartFile> videos, Post post) throws IOException {
        for (MultipartFile video: videos) {
            byte[] bytes = video.getBytes();
            String newFileName = this.generateNewFileName(video, post);
            Path path = Paths.get(pathVideosPost + "/" + newFileName);
            Files.write(path, bytes);
            this.saveFileReferenceInDatabase(newFileName, post);
        }
    }

    private void saveFileReferenceInDatabase(String newFileName, Post post) {
        VideoPost videoPost = new VideoPost(newFileName, post);
        videoPostRepository.save(videoPost);
    }

    private String generateNewFileName(MultipartFile video, Post post) {
        String randomId = generateRandomId();
        String originalFileName = video.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String newFileName = post.getCodec() + "_" + randomId + fileExtension;

        if (videoPostRepository.findByVideoReference(newFileName).isEmpty()) {
            return newFileName;
        } else {
            return newFileName + UUID.randomUUID().toString().substring(0, 5);
        }
    }

    private String generateRandomId() {
        return UUID.randomUUID().toString().substring(0, 30);
    }
}
