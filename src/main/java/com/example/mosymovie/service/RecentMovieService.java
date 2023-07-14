package com.example.mosymovie.service;

import com.example.mosymovie.dto.MovieDto;
import com.example.mosymovie.entity.Movie;
import com.example.mosymovie.repository.MovieGenreRepository;
import com.example.mosymovie.repository.MovieRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.gson.JsonParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecentMovieService {
    private final MovieRepository movieRepository;
    private final MovieGenreRepository movieGenreRepository;

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

            List<String> genreNameList = getGenreName(getNumList(contents.get("genre_ids").toString()));
            System.out.println("5 : "+genreNameList.toString());

            String ImgUrl = "https://image.tmdb.org/t/p/w200";
            String match = "[\"]";
            Movie movie = Movie.builder()
                    .title((contents.get("title").toString()).replace("\"", ""))
                    .posterImage(ImgUrl + contents.get("poster_path").toString().replaceAll(match, ""))
                    .detail(contents.get("overview").toString())
                    .releaseDate(formatDate)
                    .genre(genreNameList.toString())
                    .build();

            movieRepository.save(movie);
            //영화 장르 부분 디비 수정해야함
        }
        return "ok";
    }

    public List<String> getGenreName(List<String> genreIdList){
        List<String> genreName = new ArrayList<String>();
        for(String id : genreIdList){
            System.out.println("3 : "+id);
            System.out.println("4 : "+movieGenreRepository.findById(Integer.valueOf(id)).get().getGenreName());
            genreName.add(movieGenreRepository.findById(Integer.valueOf(id)).get().getGenreName());
        }
        return genreName;
    }

    public List<String> getNumList(String genreId){

        String pureNumbers = genreId.replace("[", "").replace("]", "");

        List<String> numberStrings = new ArrayList<>(List.of(pureNumbers.split(",")));
        System.out.println("2 : "+ numberStrings.toString());

        return numberStrings;
    }
}
