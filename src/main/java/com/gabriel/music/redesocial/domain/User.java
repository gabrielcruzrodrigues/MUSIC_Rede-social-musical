package com.gabriel.music.redesocial.domain;

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

    @ManyToOne
    @JoinColumn(name = "imageProfile_id")
    private ImageUser imageProfile;

    @ManyToOne
    @JoinColumn(name = "imageBackground_id")
    private ImageUser imageBackground;

    private String cep;
    private String goals;
    private PhoneNumber phoneNumber;
    private String whatsapp;
    private List<InstrumentsEnum> instruments;
    private List<GenresEnum> genres;
    private List<Post> posts;
    private List<SocialMediaEnum> socialMedia;
    private List<ImageUser> photos;
    private List<VideoUser> videos;
    private List<Friends> friends;
    private List<Material> purchasedMaterials;
    private List<Material> createdMaterials;
    private Date age;
    private Date entryDate;
    private Integer shows;
    private List<AvaliabityEnum> avaliabity;
    private List<Material> saves;
}
