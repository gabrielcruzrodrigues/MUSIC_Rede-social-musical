package com.gabriel.music.redesocial.domain.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageReference;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
