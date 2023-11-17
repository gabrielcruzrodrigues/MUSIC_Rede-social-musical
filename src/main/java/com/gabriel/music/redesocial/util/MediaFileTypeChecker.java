package com.gabriel.music.redesocial.util;

import com.gabriel.music.redesocial.service.user.exceptions.FileNullContentException;
import org.springframework.web.multipart.MultipartFile;

public class MediaFileTypeChecker {

    public static Boolean verifyIfIsAPhoto(MultipartFile file) throws FileNullContentException {
        return isPhoto(file);
    }

    private static boolean isPhoto(MultipartFile file) throws FileNullContentException {
        String fileName = file.getOriginalFilename();
        if (file != null) {
            return fileName.endsWith(".JPG") || fileName.endsWith(".jpg") || fileName.endsWith("jpeg") || fileName.endsWith(".png");
        } else {
            throw new FileNullContentException();
        }
    }

    public static Boolean verifyIfIsAVideo(MultipartFile file) throws FileNullContentException {
        return isVideo(file);
    }

    private static boolean isVideo(MultipartFile file) throws FileNullContentException {
        String fileName = file.getOriginalFilename();
        if (file != null) {
            return fileName.endsWith(".mp4") || fileName.endsWith(".mkv");
        } else {
            throw new FileNullContentException();
        }
    }
}
