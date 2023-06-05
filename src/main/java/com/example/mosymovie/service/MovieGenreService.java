package com.example.mosymovie.service;

import com.example.mosymovie.entity.Genre;
import com.example.mosymovie.repository.MovieGenreRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieGenreService {

    private final MovieGenreRepository movieGenreRepository;

    public String getMovieGenreInfo(String result) throws ParseException {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
        JsonArray GenreList = (JsonArray) jsonObject.get("genres");

        for (int k = 0; k < GenreList.size(); k++) {
            JsonObject contents = (JsonObject) GenreList.get(k);

            Genre genre = Genre.builder()
                    .id(contents.get("id").getAsInt())
                    .genreName(contents.get("name").toString())
                    .build();
            movieGenreRepository.save(genre);
        }
        return "ok";
    }
}
