package com.gabriel.music.redesocial.util;

import org.jcodec.api.JCodecException;
import org.jcodec.common.Demuxer;
import org.jcodec.common.io.NIOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MediaFileTypeChecker {

    public static Boolean verifyIfIsAPhoto(MultipartFile file) {
        return isPhoto(file);
    }

    private static boolean isPhoto(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        return fileName.endsWith(".jpg") || fileName.endsWith("jpeg") || fileName.endsWith(".png");
    }

    public static Boolean verifyIfIsAVideo(MultipartFile file) {
        return isVideo(file);
    }

    private static boolean isVideo(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        return fileName.endsWith(".mp4") || fileName.endsWith(".mkv");
    }
}
