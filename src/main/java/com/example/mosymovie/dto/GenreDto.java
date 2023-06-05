package com.example.mosymovie.dto;

import com.example.mosymovie.entity.Genre;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenreDto {
    private int id;
    @NotNull
    private String genreName;

    @Builder
    public GenreDto(int id, String genreName){
        this.id = id;
        this.genreName = genreName;
    }

    public Genre toEntity(){
        Genre genre = Genre.builder()
                .id(id)
                .genreName(genreName)
                .build();
        return genre;
    }
}
