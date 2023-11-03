package com.gabriel.music.redesocial.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;
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
//    private String title;
//    private String description;
////    private List<ImagePost> images;
////    private List<VideosPost> videos;
//    private Date CreatedDate;
//
//
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;
//
////    private List<Tags> tags;
//    private BigInteger numbersClick;
//    private BigInteger likes;
//    private Integer shares;
//    private Integer amountSaved;
}

