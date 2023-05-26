package com.example.mosymovie.controller;

import com.example.mosymovie.entity.Movie;
import com.example.mosymovie.repository.MovieRepository;
import com.example.mosymovie.service.RecentMovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RecentMovieController {

    private final RecentMovieService recentMovieService;
    private final MovieRepository movieRepository;
    private String result;
    private static final String API_KEY = "2e6123897022f9724a9292e10cdc618c";

    @GetMapping("api/getRecentMoviesInfo")
    @ResponseBody
    public String getRecentMoviesInfo(){
        int pages = 1;

        try{
            for(int i = 1;i <= 1; i++){
                String apiURL = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY +
                        "&watch_region=KR";

                URL url = new URL(apiURL);

                BufferedReader bf;
                bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                result = bf.readLine();
                recentMovieService.getRecentMoviesInfo(result);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "okay";
    }

    @GetMapping("api/recentMovies")
    public List<Movie> getAllMovies(){

        List<Movie> movies = movieRepository.findAll();
        return movies;
    }
}
