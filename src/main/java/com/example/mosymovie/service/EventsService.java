package com.example.mosymovie.service;


import com.example.mosymovie.entity.Events;
import com.example.mosymovie.entity.Movie;
import com.example.mosymovie.repository.EventsRepository;
import com.example.mosymovie.repository.MovieRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class EventsService {
    private final EventsRepository eventsRepository;
    private final MovieRepository movieRepository;
    private WebDriver driver;
    private boolean check = false;
    private static String event_url = "https://event.lottecinema.co.kr/NLCHS/Event/DetailList?code=20#none";
    @PersistenceContext
    private EntityManager entityManager;

    public String getEventsData() throws IOException{

        List<Events> eventsList = new ArrayList<Events>();

        try{
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-popup-blocking");
            options.addArguments("headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--blink-settings=imagesEnabled=false");
            options.addArguments("--user-agent=Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");

            driver = new ChromeDriver(options);
            driver.get(event_url);

            List<WebElement> contents = new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver -> driver.findElements(By.cssSelector("#contents > ul > li")));
            while(contents.size() > 0){
                for(WebElement content: contents){
                    String extractTitle = "";
                    Pattern pattern = Pattern.compile("<(.*?)>");
                    Matcher matcher = pattern.matcher(content.findElement(By.cssSelector("a img")).getAttribute("alt"));

                    while(matcher.find()){
                        extractTitle = matcher.group(1);
                    }

                    String movieGenre = getMovieGenre(extractTitle);

                    Events events = Events.builder()
                            .url(content.findElement(By.tagName("a")).getAttribute("href"))
                            .image(content.findElement(By.cssSelector("a img")).getAttribute("src"))
                            .title(content.findElement(By.cssSelector("a img")).getAttribute("alt"))
                            .period(content.findElement(By.cssSelector("a div.itm_date")).getText())
                            .movieTitle(extractTitle)
                            .genre(movieGenre)
                            .build();
                    boolean hasSameTitle = false;
                    for(Events e : eventsList){
                        if (e.getTitle().equals(events.getTitle())) {
                            hasSameTitle = true;
                            break;
                        }
                    }
                    if(!hasSameTitle){
                        eventsList.add(events);
                    }
                }
                WebElement btnMore = driver.findElement(By.cssSelector(("#contents > button.btn_txt_more")));
                btnMore.click();

                Thread.sleep(1000);
                contents = driver.findElements(By.cssSelector("#contents > ul > li"));
            }
        }catch (Exception e){
            System.out.println("CGV Crawling error: "+e.toString());
        }

        String checkTableQuery = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'events'";
        Long count = (Long) entityManager.createNativeQuery(checkTableQuery).getSingleResult();
        BigInteger bigIntegerCount = BigInteger.valueOf(count);
        boolean tableExists = bigIntegerCount.intValue() > 0;

        if(tableExists) {
            String deleteQuery = "DELETE FROM movie.events";
            entityManager.createNativeQuery(deleteQuery).executeUpdate();
        }

        for(Events e : eventsList){
            eventsRepository.save(e);
        }
        return "okay";
    }

    public List<Events> getAllEvents(){
        List<Events> events = eventsRepository.findAll();
        return events;
    }

    public String getMovieGenre(String movieTitle){
        String movieGenre = "";
        List<Movie> movieList = movieRepository.findAll();
        for(Movie movie : movieList){
            if(movie.getTitle().equals(movieTitle)){
                movieGenre = movie.getGenre();
                System.out.println("1: "+movieGenre);
            }
        }
        return movieGenre;
    }
}
