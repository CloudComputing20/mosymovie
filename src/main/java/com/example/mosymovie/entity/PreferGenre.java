package com.example.mosymovie.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class PreferGenre {

    @Id
    private String userID;

    @Column
    private String preferMovie;


    @Builder
    public PreferGenre(String preferMovie, String userID){
        this.preferMovie = preferMovie;
        this.userID = userID;
    }
}
