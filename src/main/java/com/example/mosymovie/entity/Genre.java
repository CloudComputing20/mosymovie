package com.example.mosymovie.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Genre {

    @Id
    private int id;

    @Column(nullable = false)
    private String genreName;

    @Builder
    public Genre(int id, String genreName){
        this.id = id;
        this.genreName = genreName;
    }
}
