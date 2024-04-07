package com.marosmamrak.consumeapi.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "post")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

}
