package com.example.mosymovie.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventID;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String movieTitle;

    @Column(nullable = false)
    private String period;

    @Column
    private String image;

    @Builder
    public Events(String url, String title, String period, String image, String movieTitle){
        this.image = image;
        this.url = url;
        this.title = title;
        this.period = period;
        this.movieTitle = movieTitle;
    }
}
