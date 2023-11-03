package com.gabriel.music.redesocial.domain.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideosPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String videoReference;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
