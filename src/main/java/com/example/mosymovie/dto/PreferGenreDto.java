package com.example.mosymovie.dto;

import com.example.mosymovie.entity.preferGenre;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private int genreID;
    @NotNull
    private String genreName;

    @Builder
    public MovieDto(String genreName){
        this.genreName = genreName;
    }

    public preferGenre toEntity(){
        preferGenre genre = preferGenre.builder()
                .genreName(genreName)
                .build();
        return genre;
    }
}
