package com.example.mosymovie.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
public class PreferGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int preferGenreID;

    @Column(nullable = false)
    private String genreName;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User userID;

    @Builder
    public PreferGenre(String genreName, User userID){
        this.genreName = genreName;
        this.userID = userID;
    }
}
