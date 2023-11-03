package com.gabriel.music.redesocial.domain.post;

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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Date CreatedDate;
    private Long numbersClick;
    private Long likes;
    private Integer shares;
    private Integer amountSaved;

//    @ManyToMany
//    @JoinTable(name = "post_tags", joinColumns = @JoinColumn(name = ))
//    private List<Tags> tags;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @OneToMany(mappedBy = "post")
    private List<VideosPost> videos;

    @OneToMany(mappedBy = "post")
    private List<ImagePost> images;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}

