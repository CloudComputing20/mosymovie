package com.example.mosymovie.scheduler;

import com.example.mosymovie.service.RecentMovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


@RequiredArgsConstructor
@Component
public class recentMovieScheduler {
    private final RecentMovieService recentMovieService;
    private String result;
    private static final String API_KEY = "2e6123897022f9724a9292e10cdc618c";

    @Scheduled(cron = "0 0 12 * * ?") //매일 정오 12:00에 실행
    public void getRecentMoviesInfo(){

        try{
            for(int i = 1;i <= 1; i++){
                String apiURL = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY +
                        "&watch_region=KR&language=ko-KR";

                URL url = new URL(apiURL);

                BufferedReader bf;
                bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                result = bf.readLine();
                recentMovieService.getRecentMoviesInfo(result);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
