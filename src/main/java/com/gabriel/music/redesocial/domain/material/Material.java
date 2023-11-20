package com.gabriel.music.redesocial.domain.material;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabriel.music.redesocial.domain.enums.*;
import com.gabriel.music.redesocial.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String name;
    private String referenceFileName;
    private LocalDateTime createdDate;
    private String description;
    private Long amountDownloads;
    private String size;
    @Column(nullable = false)
    private Float price;

    @Enumerated(EnumType.STRING)
    private InstrumentsEnum instruments;

    @Enumerated(EnumType.STRING)
    private GenreEnum genres;

    @Enumerated(EnumType.STRING)
    private NivelEnum nivelEnum;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "createdMaterialsUser_id")
    private User createdMaterialsUser_id;

    public Material(String name, String description, Float price, InstrumentsEnum instrumentsEnum, GenreEnum genreEnum, NivelEnum nivelEnum, User user) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.instruments = instrumentsEnum;
        this.genres = genreEnum;
        this.nivelEnum = nivelEnum;
        this.createdMaterialsUser_id = user;
    }

//    @JsonIgnore
//    @ManyToMany
//    @JoinColumn(name = "savesUser_id")
//    private User savesUser_id;
}
