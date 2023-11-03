package com.gabriel.music.redesocial.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class ImageUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String imageReference;

    @OneToOne(mappedBy = "imageProfile")
    private User idUserProfile;
    @OneToOne(mappedBy = "imageBackground")
    private User IdUserBackground;
}
