package com.example.mosymovie.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int genreID;

    @Column(nullable = false)
    private String genreName;

    @Builder
    public Movie(String genreName){
        this.genreName = genreName;
    }
}
