package com.gabriel.music.redesocial.domain.material;

import com.gabriel.music.redesocial.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
