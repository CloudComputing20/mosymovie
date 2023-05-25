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
@AllArgsConstructor
public class PreferGenreDto {

    private int preferGenreID;
    @NotNull
    private String genreName;

    @NotNull
    private User userID;

    @Builder
    public PreferGenreDto(String genreName, User userID){
        this.genreName = genreName;
        this.userID = userID;
    }

    public PreferGenre toEntity(){
        PreferGenre genre = PreferGenre.builder()
                .genreName(genreName)
                .userID(userID)
                .build();
        return genre;
    }
}
