package com.example.mosymovie.service;


import com.example.mosymovie.entity.Movie;
import com.example.mosymovie.entity.PreferGenre;
import com.example.mosymovie.repository.MovieRepository;
import com.example.mosymovie.repository.PreferGenreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.apache.commons.io.IOUtils;
import org.tensorflow.TensorFlow;
import org.tensorflow.op.core.TensorArrayRead;
import org.tensorflow.proto.framework.GraphDef;
import org.tensorflow.types.TString;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecommendService {

    private final PreferGenreRepository preferGenreRepository;
    private final MovieRepository movieRepository;
    private Graph graph;
    private Session session;

    public List<Movie> getRecommendMovies(PreferGenre userPrefer){
        String preferMovieStr = userPrefer.getPreferMovie();
        System.out.println("preferMovieStr"+preferMovieStr);
        List<String> preferMovieList = List.of(preferMovieStr.split(","));
        List<Movie> pickMovie = new ArrayList<>();
        for(String pickGenre : preferMovieList){
            System.out.println("pickGenre"+pickGenre);
            List<Movie> movie = movieRepository.findAll();
            for(Movie m : movie){
                String genre = m.getGenre();
                List<String> genreList = List.of(genre.replace("[", "").replace("]", "").split(","));
                for(String genreStr : genreList){
                    System.out.println("genreStr : "+genreStr);
                    if(genreStr.equals(pickGenre)){
                        pickMovie.add(m);
                    }
                }
            }
        }

        return pickMovie;
    }

    public void TensorFlowService(){
        try(InputStream is = getClass().getResourceAsStream("/saved_model.pb")){
            graph = new Graph();
            graph.importGraphDef(GraphDef.parseFrom(IOUtils.toByteArray(is)));
            session = new Session(graph);
        }catch(IOException e){
            throw new RuntimeException("모델 파일을 불러 올 수 없습니다.");
        }
    }

/*    public String[] predict(String[] inputdata){
        try(Tensor<String> input = Tensor.create(new long[]{1, inputData.length}, StringBuffer.wrap(inputData))
        Tensor<String> output = session.runner().feed("input", input).fetch("output").run().get(0).expect(String.class)){
            String[] result = new String[output.numElements()];
            output.copyTo(result);
            return result;
        }
    }*/


/*    public List<String> postprocessData(Result outputTensorList, int userID) {
        System.out.println("tensorList : "+outputTensorList.toString());
        List<String> AIList = new ArrayList<>();
        List<String> preferMovieList = new ArrayList<>();
        for(Map.Entry<String, Tensor> tensor : outputTensorList){
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
    }*/

   /* public void saveRepository(List<String> AIList){
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
    }*/
}
