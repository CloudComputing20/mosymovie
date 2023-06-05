package com.example.mosymovie.controller;

import com.example.mosymovie.service.MovieGenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Slf4j
@RequiredArgsConstructor
@RestController
public class GenreController {

    private final MovieGenreService movieGenreService;
    private static final String API_KEY = "2e6123897022f9724a9292e10cdc618c";
    private String result;

    @GetMapping("api/getMovieGenreInfo")
    @ResponseBody
    public String getMovieGenreInfo(){

        try{
            for(int i = 1;i <= 1; i++){
                String apiURL = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + API_KEY +
                        "&watch_region=KR";

                URL url = new URL(apiURL);

                BufferedReader bf;
                bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                result = bf.readLine();
                movieGenreService.getMovieGenreInfo(result);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "okay";
    }
}
