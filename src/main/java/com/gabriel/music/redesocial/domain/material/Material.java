package com.gabriel.music.redesocial.domain.material;

import com.gabriel.music.redesocial.domain.enums.*;
import com.gabriel.music.redesocial.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String name;
    private Date CreatedDate;
    private String description;
    private Long amountDownloads;
    private String size;
    private Float price;

    @OneToMany(mappedBy = "material")
    private List<VideoMaterial> videos;

    @OneToMany(mappedBy = "material")
    private List<ImageMaterial> images;

    @Enumerated(EnumType.STRING)
    private TypeEnum type;

    @Enumerated(EnumType.STRING)
    private List<InstrumentsEnum> instruments;

    @Enumerated(EnumType.STRING)
    private List<GenreEnum> genres;

    @Enumerated(EnumType.STRING)
    private NivelEnum nivelEnum;

    @Enumerated(EnumType.STRING)
    private FreeOrNoEnum freeOrNoEnum;

    @ManyToOne
    @JoinColumn(name = "purchasedMaterialsUser_id")
    private User purchasedMaterialsUser_id;

    @ManyToOne
    @JoinColumn(name = "createdMaterialsUser_id")
    private User createdMaterialsUser_id;

    @ManyToOne
    @JoinColumn(name = "savesUser_id")
    private User savesUser_id;
}
