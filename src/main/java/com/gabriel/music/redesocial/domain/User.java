package com.gabriel.music.redesocial.domain;

import com.gabriel.music.redesocial.domain.enums.AvaliabityEnum;
import com.gabriel.music.redesocial.domain.enums.GenreEnum;
import com.gabriel.music.redesocial.domain.enums.InstrumentsEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true, nullable = false)
    @NotNull
    @NotBlank(message = "o campo username não pode estar nulo.")
    private String username;

    @Column(unique = true, nullable = false)
    @Email
    @NotNull(message = "o campo email não pode estar nulo.")
    private String email;

    @NotBlank
    @NotNull(message = "O campo password não pode estar nulo.")
    @Size(min = 8, max = 60)
    private String password;

    private String cep;
    private String goals;
    private String whatsapp;
    private Date age;
    private Date entryDate;
    private Integer shows;

    @Enumerated(value = EnumType.STRING)
    private List<GenreEnum> genre;

    @Enumerated(EnumType.STRING)
    private List<InstrumentsEnum> instruments;

    @Enumerated(EnumType.STRING)
    private List<AvaliabityEnum> availability;

    @OneToMany(mappedBy = "user")
    private List<SocialMedia> socialMedia;

    @OneToMany(mappedBy = "creator")
    private List<Post> posts;

    @OneToOne(mappedBy = "userProfile")
    private ImageUser imageProfile;

    @OneToOne(mappedBy = "userBackground")
    private ImageUser imageBackground;

    @OneToOne(mappedBy = "user")
    private PhoneNumber phoneNumber;

    @OneToMany(mappedBy = "idUserPhoto")
    private List<ImageUser> photos;

    @OneToMany(mappedBy = "user")
    private List<VideoUser> videos;

    @OneToMany(mappedBy = "user")
    private List<Friend> friends;

    @OneToMany(mappedBy = "purchasedMaterialsUser_id")
    private List<Material> purchasedMaterials;

    @OneToMany(mappedBy = "createdMaterialsUser_id")
    private List<Material> createdMaterials;

    @OneToMany(mappedBy = "savesUser_id")
    private List<Material> saves;
}
