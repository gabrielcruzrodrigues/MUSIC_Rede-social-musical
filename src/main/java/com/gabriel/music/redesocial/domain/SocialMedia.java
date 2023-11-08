package com.gabriel.music.redesocial.domain;

import com.gabriel.music.redesocial.domain.enums.SocialMediaEnum;
import com.gabriel.music.redesocial.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @Column(nullable = false)
    private SocialMediaEnum socialMedia;

    @Column(length = 60, nullable = false)
    @NotNull
    @NotBlank(message = "O username da sua rede social não pode ser nulo")
    private String usernameSocialMedia;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
