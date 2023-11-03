package com.gabriel.music.redesocial.domain.user;

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

    @OneToOne
    @JoinColumn(name = "id_imageProfile")
    private User userProfile;

    @OneToOne
    @JoinColumn(name = "id_imageBackground")
    private User userBackground;

    @ManyToOne
    @JoinColumn(name = "id_UserPhoto")
    private User idUserPhoto;
}
