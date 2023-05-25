package com.example.mosymovie.dto;


import com.example.mosymovie.entity.Movie;
import com.example.mosymovie.entity.PreferGenre;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private int movieID;
    @NotNull
    private String title;
    @NotNull
    private Date releaseDate;
    @NotNull
    private String genre;
    private String detail;
    private String posterImage;


    @Builder
    public MovieDto(String title, String genre, String detail, Date releaseDate, String posterImage){
        this.title = title;
        this.genre = genre;
        this.detail = detail;
        this.releaseDate = releaseDate;
        this.posterImage = posterImage;
    }

    public Movie toEntity(){
        Movie movie = Movie.builder()
                .title(title)
                .genre(genre)
                .detail(detail)
                .posterImage(posterImage)
                .releaseDate(releaseDate)
                .build();
        return movie;
    }
}
