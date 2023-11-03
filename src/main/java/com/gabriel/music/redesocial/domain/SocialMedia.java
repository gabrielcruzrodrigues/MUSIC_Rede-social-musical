package com.gabriel.music.redesocial.domain;

import com.gabriel.music.redesocial.domain.enums.SocialMediaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private SocialMediaEnum socialMedia;
    private String username;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
