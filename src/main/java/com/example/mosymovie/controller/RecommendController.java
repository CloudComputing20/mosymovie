package com.example.mosymovie.controller;

import com.example.mosymovie.entity.Movie;
import com.example.mosymovie.entity.PreferGenre;
import com.example.mosymovie.repository.PreferGenreRepository;
import com.example.mosymovie.service.RecommendService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.conscrypt.io.IoUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tensorflow.*;
import org.tensorflow.proto.framework.GraphDef;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
public class RecommendController {

    private final RecommendService recommendService;
    private final PreferGenreRepository preferGenreRepository;

    @GetMapping("recommendations/{userID}")
    public List<Movie> getRecommendations(@PathVariable String userID){
        System.out.println(userID);
        PreferGenre userPrefer = preferGenreRepository.findById(userID).get();
        List<Movie> movieList = recommendService.getRecommendMovies(userPrefer);

        return movieList;
    }

    @PostMapping("recommendations/ai")
    public List<Movie> getRecommendationsAI() {
        recommendService.TensorFlowService();

        List<Movie> movieList = new ArrayList<Movie>();

        return movieList;
    }
}
