package com.example.mosymovie.controller;

import com.example.mosymovie.entity.Movie;
import com.example.mosymovie.entity.PreferGenre;
import com.example.mosymovie.repository.PreferGenreRepository;
import com.example.mosymovie.service.RecommendService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tensorflow.*;

import java.io.IOException;
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
    public List<String> getRecommendations(@PathVariable String userID){
        PreferGenre userPrefer = preferGenreRepository.findById(userID).get();
        List<Movie> movieList = recommendService.getRecommendMovies(userPrefer);
        List<String> poster = new ArrayList<>();
        for(Movie m : movieList){
            poster.add(m.getPosterImage());
        }

        return poster;
    }

    /*@PostMapping("recommendations")
    public ResponseEntity<float[]> getRecommendations(@RequestBody float[] inputData) {
        *//*try{
            float[] result = recommendService.predict(inputData);
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }*//*

        *//*        //tensorflow 모델 로드
        SavedModelBundle model = SavedModelBundle.load(new ClassPathResource("tensorflowFile").getFile().getPath(), "serve");

        //추천 알고리즘 실행
        Session.Runner runner = model.session().runner();
        Shape userShape = Shape.of(Integer.MIN_VALUE, Integer.MAX_VALUE);
        Shape movieShape = Shape.of(Integer.MIN_VALUE, Integer.MAX_VALUE);
        Tensor u = Tensor.of(TString.class, userShape);
        Tensor m = Tensor.of(TString.class, movieShape);

        Iterator<GraphOperation> operationIterator = model.graph().operations();
        while(operationIterator.hasNext()){
            System.out.println(operationIterator.next().name());
        }
        System.out.println("version: "+TensorFlow.version());
        Result outputTensorList = runner
                .feed("saver_filename", u)
                .feed("saver_filename", m)
                .fetch("StatefulPartitionedCall_2").run();
        System.out.println(outputTensorList);

        List<String> recommendations = recommendService.postprocessData(outputTensorList, userID);

        model.close();

        return recommendations;*//*
    }*/
}
