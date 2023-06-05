package com.example.mosymovie.controller;

import com.example.mosymovie.entity.Movie;
import com.example.mosymovie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RecentMovieController {
    private final MovieRepository movieRepository;

    @GetMapping("api/recentMovies")
    public List<String> getAllMovies(){

        List<Movie> movies = movieRepository.findAll();
        List<String> movies_poster = new ArrayList<String>();
        for(int i = 0;i < movies.size();i++){
            movies_poster.add(movies.get(i).getPosterImage());
        }
        return movies_poster;
    }
}
