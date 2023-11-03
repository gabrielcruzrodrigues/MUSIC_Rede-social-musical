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
    private List<ImagePost> images;
////    private List<VideosPost> videos;
    private Date CreatedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

////    private List<Tags> tags;
    private Long numbersClick;
    private Long likes;
    private Integer shares;
    private Integer amountSaved;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}

