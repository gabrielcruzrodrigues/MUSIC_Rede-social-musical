package com.gabriel.music.redesocial.domain;

import com.gabriel.music.redesocial.domain.enums.AvaliabityEnum;
import com.gabriel.music.redesocial.domain.enums.GenresEnum;
import com.gabriel.music.redesocial.domain.enums.InstrumentsEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String cep;
    private String goals;
    private String whatsapp;
    private Date age;
    private Date entryDate;
    private Integer shows;

    @Enumerated(EnumType.STRING)
    private List<GenresEnum> genres;

    @OneToMany
    @JoinColumn(name = "socialMedia_id")
    private List<SocialMedia> socialMedia;

    @Enumerated(EnumType.STRING)
    private List<InstrumentsEnum> instruments;

    @Enumerated(EnumType.STRING)
    private List<AvaliabityEnum> availability;

    private List<Post> posts;

    @OneToOne
    @JoinColumn(name = "imageProfile_id")
    private ImageUser imageProfile;

    @OneToOne
    @JoinColumn(name = "imageBackground_id")
    private ImageUser imageBackground;

    @OneToOne
    @JoinColumn(name = "number_id")
    private PhoneNumber phoneNumber;

    @OneToMany
    @JoinColumn(name = "photos_id")
    private List<ImageUser> photos;

    @OneToMany
    @JoinColumn(name = "videos_id")
    private List<VideoUser> videos;

    private List<Friends> friends;
    private List<Material> purchasedMaterials;
    private List<Material> createdMaterials;
    private List<Material> saves;
}
