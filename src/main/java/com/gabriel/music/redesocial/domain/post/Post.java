package com.gabriel.music.redesocial.domain.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabriel.music.redesocial.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDate createdDate;
//    private List<Tag> tags;
    private Long numbersClick;
    private Long likes;
    private Integer shares;
    private Integer amountSaved;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @OneToMany(mappedBy = "post")
    private List<VideosPost> videos;

    @OneToMany(mappedBy = "post")
    private List<ImagePost> images;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;


    //basic post
    public Post(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.creator = user;
    }
}

