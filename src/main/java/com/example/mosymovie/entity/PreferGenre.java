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
    private int userID;

    @Column
    private String preferMovie;


    @Builder
    public PreferGenre(String preferMovie, int userID){
        this.preferMovie = preferMovie;
        this.userID = userID;
    }
}
