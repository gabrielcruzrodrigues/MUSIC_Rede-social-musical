package com.gabriel.music.redesocial.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class ImageUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageReference;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_imageProfile")
    private User userImageProfile;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_imageBackground")
    private User userBackground;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_UserPhoto")
    private User idUserPhotos;

    public ImageUser(String imageReference, User userImageProfile, User userBackground, User idUserPhotos) {
        this.imageReference = imageReference;
        this.userImageProfile = userImageProfile;
        this.userBackground = userBackground;
        this.idUserPhotos = idUserPhotos;
    }
}
