package com.example.mosymovie.controller;

import com.example.mosymovie.service.RecommendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
public class RecommendController {

    private final RecommendService recommandService;

    @GetMapping("recommendations/{userID}")
    public List<String> getRecommendations(@PathVariable int userID){
        //tensorflow 모델 로드
        SavedModelBundle model = SavedModelBundle.load("classpath:/tensorflowFile/movie_recommendation/1", "serve");

        //추천 알고리즘 실행
        Session.Runner runner = model.session().runner();

        List<Tensor<?>> outputTensorList = runner.fetch("output").run();

        List<String> recommendations = recommandService.postprocessData(outputTensorList, userID);

        model.close();

        return recommendations;
    }
}
