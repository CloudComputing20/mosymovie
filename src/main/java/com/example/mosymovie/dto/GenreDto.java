package com.example.mosymovie.dto;

import com.example.mosymovie.entity.Genre;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectGenreDto {

    private int genreID;
    @NotNull
    private String genreName;

    @Builder
    public SelectGenreDto(String genreName){
        this.genreName = genreName;
    }

    public Genre toEntity(){
        Genre genre = Genre.builder()
                .genreName(genreName)
                .build();
        return genre;
    }
}
