package com.gabriel.music.redesocial.domain.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabriel.music.redesocial.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15)
    private String codec;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDateTime createdDate;
//    private List<Tag> tags;
    private Long likes;
    private Integer shares;
    private Integer amountSaved;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @OneToMany(mappedBy = "post")
    private List<VideoPost> videos;

    @OneToMany(mappedBy = "post")
    private List<ImagePost> images;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    public Post(String title, String description, User user) {
        this.codec = UUID.randomUUID().toString().substring(0, 15);
        this.title = title;
        this.description = description;
        this.creator = user;

        this.createdDate = LocalDateTime.now();
        this.likes = 0L;
        this.shares = 0;
        this.amountSaved = 0;
    }

    public Post(String title, String description, User user, List<ImagePost> image, List<VideoPost> video) {
        this.title = title;
        this.description = description;
        this.creator = user;
        this.images = image;
        this.videos = video;

        this.createdDate = LocalDateTime.now();
        this.likes = 0L;
        this.shares = 0;
        this.amountSaved = 0;
    }
}

