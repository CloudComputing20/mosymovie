package com.example.mosymovie.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Blob;
import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
@DynamicUpdate
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieID;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @Column(columnDefinition = "LONGTEXT")
    private String genre;

    @Column(columnDefinition = "LONGTEXT")
    private String detail;

    @Column()
    private String posterImage;


    @Builder
    public Movie(String title, String genre, String detail, Date releaseDate, String posterImage){
        this.title = title;
        this.genre = genre;
        this.detail = detail;
        this.releaseDate = releaseDate;
        this.posterImage = posterImage;
    }

    public void UserUpdate(String title, String genre, String detail, Date releaseDate, String posterImage){
        this.title = title;
        this.genre = genre;
        this.detail = detail;
        this.releaseDate = releaseDate;
        this.posterImage = posterImage;
    }
}
