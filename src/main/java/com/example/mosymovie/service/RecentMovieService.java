package com.example.mosymovie.service;

import com.example.mosymovie.dto.MovieDto;
import com.example.mosymovie.entity.Movie;
import com.example.mosymovie.repository.MovieRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.gson.JsonParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class RecentMovieService {
    private static final String API_KEY = "2e6123897022f9724a9292e10cdc618c";
    private final MovieRepository movieRepository;

    public String getRecentMoviesInfo(String result) throws ParseException {
        JsonArray list = null;
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
        list = (JsonArray) jsonObject.get("results");
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int k = 0; k < list.size(); k++) {
            JsonObject contents = (JsonObject) list.get(k);

            String oldDate = contents.get("release_date").toString();
            String newDate = oldDate.replaceAll("\"", "");
            Date formatDate = dtFormat.parse(newDate);


            String ImgUrl = "https://image.tmdb.org/t/p/w200";
            String match = "[\"]";
            Movie movie = Movie.builder()
                    .title(contents.get("title").toString())
                    .posterImage(ImgUrl + contents.get("poster_path").toString().replaceAll(match, ""))
                    .detail(contents.get("overview").toString())
                    .releaseDate(formatDate)
                    .genre(contents.get("genre_ids").toString())
                    .build();
            movieRepository.save(movie);
            //영화 장르 부분 디비 수정해야함
        }
        return "ok";
    }
}
