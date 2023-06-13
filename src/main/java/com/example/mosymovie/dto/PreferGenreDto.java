package com.example.mosymovie.dto;

import com.example.mosymovie.entity.User;
import com.example.mosymovie.entity.PreferGenre;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PreferGenreDto {
    private String preferMovie;
    private String userID;

    @Builder
    public PreferGenreDto(String preferMovie, String userID){
        this.preferMovie = preferMovie;
        this.userID = userID;
    }

    public PreferGenre toEntity(){
        PreferGenre genre = PreferGenre.builder()
                .preferMovie(preferMovie)
                .userID(userID)
                .build();
        return genre;
    }
}
