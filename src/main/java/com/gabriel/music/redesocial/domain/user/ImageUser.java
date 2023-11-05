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
    private User userProfile;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_imageBackground")
    private User userBackground;

    @ManyToOne
    @JoinColumn(name = "id_UserPhoto")
    private User idUserPhoto;

    public ImageUser(String imageReference, User userProfile, User userBackground, User idUserPhoto) {
        this.imageReference = imageReference;
        this.userProfile = userProfile;
        this.userBackground = userBackground;
        this.idUserPhoto = idUserPhoto;
    }
}
