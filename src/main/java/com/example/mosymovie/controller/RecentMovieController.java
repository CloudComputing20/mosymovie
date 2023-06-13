package com.example.mosymovie.controller;

import com.example.mosymovie.entity.Movie;
import com.example.mosymovie.repository.MovieRepository;
import jakarta.servlet.http.HttpServletRequest;
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
    public List<Movie> getAllMovies(){

        List<Movie> movies = movieRepository.findAll();
        return movies;
    }
}
