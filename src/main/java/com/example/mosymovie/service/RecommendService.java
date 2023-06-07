package com.example.mosymovie.service;


import com.example.mosymovie.entity.Movie;
import com.example.mosymovie.entity.PreferGenre;
import com.example.mosymovie.repository.MovieRepository;
import com.example.mosymovie.repository.PreferGenreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tensorflow.Tensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecommendService {

    private final PreferGenreRepository preferGenreRepository;
    private final MovieRepository movieRepository;
    public List<String> postprocessData(List<Tensor<?>> outputTensorList, int userID) {
        System.out.println("tensorList : "+outputTensorList.toString());
        List<String> AIList = new ArrayList<>();
        List<String> preferMovieList = new ArrayList<>();
        for(Tensor<?> tensor : outputTensorList){
            AIList.add(tensor.toString());
        }
        saveRepository(AIList);
        Optional<PreferGenre> inquiryData = preferGenreRepository.findById(userID);
        PreferGenre userPrefer;
        if(inquiryData.isPresent()){
            userPrefer = inquiryData.get();
            preferMovieList = findPreferMovieList(userPrefer);
        }

        return preferMovieList;
    }

    public void saveRepository(List<String> AIList){
        for(String each : AIList){
            String replacedStr = each.replace("[", "").replace("]", "");
            List<String> userList = new ArrayList<>(List.of(replacedStr.split(",")));
            System.out.println("userList : "+ userList);

            String movieName = userList.get(0);
            for(int i = 1; i < userList.size(); i++){
                int userID = Integer.parseInt(userList.get(i));

                Optional<PreferGenre> existingUser = preferGenreRepository.findById(userID);
                PreferGenre preferGenre;
                if(existingUser.isPresent()){
                    preferGenre = existingUser.get();
                    String beforeData = preferGenre.getPreferMovie();
                    String afterData = beforeData+","+movieName;
                    preferGenre.setPreferMovie(afterData);
                }else{
                    preferGenre = PreferGenre.builder()
                            .userID(userID)
                            .preferMovie(movieName)
                            .build();
                }
                preferGenreRepository.save(preferGenre);
            }
        }
    }

    public List<String> findPreferMovieList(PreferGenre userPrefer){
        String preferMovieStr = userPrefer.getPreferMovie();
        List<String> preferMovieList = new ArrayList<>(List.of(preferMovieStr.split(",")));
        List<Movie> movies = new ArrayList<>();
        for(String movieName : preferMovieList){
            Movie targetMovie = movieRepository.findByTitle(movieName);
            movies.add(targetMovie);
        }

        List<String> movies_poster = new ArrayList<>();
        if(movies != null)
        {
            for(int i = 0;i < movies.size();i++){
                movies_poster.add(movies.get(i).getPosterImage());
                System.out.println("recommendation movies!");
            }
        }
        else{
            List<Movie> basicMovieList = movieRepository.findAll();
            for(int i = 0;i < basicMovieList.size();i++){
                movies_poster.add(basicMovieList.get(i).getPosterImage());
                System.out.println("There is no recommendation movie so, It may show basic movie poster");
            }
        }
        return movies_poster;
    }
}
