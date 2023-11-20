package com.gabriel.music.redesocial.domain.material;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabriel.music.redesocial.domain.enums.*;
import com.gabriel.music.redesocial.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String codec;
    @Column(length = 100, nullable = false)
    private String name;
    private String referenceFileName;
    @Column(nullable = false)
    private LocalDateTime createdDate;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Long amountDownloads;
    private String size;
    @Column(nullable = false)
    private Float price;

    @Enumerated(EnumType.STRING)
    private InstrumentsEnum instruments;

    @Enumerated(EnumType.STRING)
    private Genre genres;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelEnum nivelEnum;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "createdMaterialsUser_id")
    private User createdMaterialsUserId;

    public Material(String name, String description, Float price, InstrumentsEnum instrumentsEnum, Genre genre, NivelEnum nivelEnum, User user) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.instruments = instrumentsEnum;
        this.genres = genre;
        this.nivelEnum = nivelEnum;
        this.createdMaterialsUserId = user;

        //default configuration
        this.createdDate = LocalDateTime.now();
        this.amountDownloads = 0L;
        this.codec = UUID.randomUUID().toString().substring(0, 15);
    }
}
