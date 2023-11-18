package com.gabriel.music.redesocial.domain.post.DTO;

import org.springframework.web.multipart.MultipartFile;

public record PostCreationDTO(String title, String description, String creator, MultipartFile image, MultipartFile video) {
}
