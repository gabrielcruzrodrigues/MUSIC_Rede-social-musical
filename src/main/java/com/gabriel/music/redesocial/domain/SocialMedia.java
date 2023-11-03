package com.gabriel.music.redesocial.domain;

import com.gabriel.music.redesocial.domain.enums.SocialMediaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private SocialMediaEnum socialMedia;
    private String username;
    @OneToOne(mappedBy = "socialMedia")
    private User idUserSocialMedia;
}
