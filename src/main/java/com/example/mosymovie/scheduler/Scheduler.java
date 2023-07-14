package com.example.mosymovie.scheduler;

import com.example.mosymovie.service.EventsService;
import com.example.mosymovie.service.RecentMovieService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;


@RequiredArgsConstructor
@Component
public class Scheduler {
    private final JdbcTemplate jdbcTemplate;
    private final RecentMovieService recentMovieService;
    private String result;
    private static final String API_KEY = "2e6123897022f9724a9292e10cdc618c";

    private final EventsService eventsService;

    @PersistenceContext
    private EntityManager entityManager;

    @Scheduled(cron = "0 0 0 * * ?")
    public void events() throws Exception{
        eventsService.getEventsData();
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?") //매일 정오 12:00에 실행
    public void getRecentMoviesInfo(){

        String checkTableQuery = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'movie'";
        Long count = (Long) entityManager.createNativeQuery(checkTableQuery).getSingleResult();
        BigInteger bigIntegerCount = BigInteger.valueOf(count);
        boolean tableExists = bigIntegerCount.intValue() > 0;

        if(tableExists) {
            String deleteQuery = "DELETE FROM movie.movie";
            entityManager.createNativeQuery(deleteQuery).executeUpdate();
        }

        try{
            int totalPages = 3;
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
