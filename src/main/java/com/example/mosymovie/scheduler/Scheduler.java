package com.example.mosymovie.scheduler;

import com.example.mosymovie.service.EventsService;
import com.example.mosymovie.service.RecentMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


@RequiredArgsConstructor
@Component
public class Scheduler {
    private final JdbcTemplate jdbcTemplate;
    private final RecentMovieService recentMovieService;
    private String result;
    private static final String API_KEY = "2e6123897022f9724a9292e10cdc618c";

    private final EventsService eventsService;

    @Scheduled(cron = "0 23 18 * * ?")
    public void events() throws Exception{
        eventsService.getEventsData();
    }

    @Scheduled(cron = "0 22 18 * * ?") //매일 정오 12:00에 실행
    public void getRecentMoviesInfo(){

        try{
            int totalPages = 5;
            String apiURL = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY +
                    "&watch_region=KR&language=ko-KR";
            for(int page = 1;page <= totalPages; page++){
                String apiUrlWithPage = apiURL + "&page="+ page;
                URL url = new URL(apiUrlWithPage);

                BufferedReader bf;
                bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                result = bf.readLine();
                recentMovieService.getRecentMoviesInfo(result);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean isExitTable(String table){
        String isExit = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";
        int count = jdbcTemplate.queryForObject(isExit, new Object[]{table}, Integer.class);

        return count > 0;
    }
}
