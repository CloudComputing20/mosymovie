package com.example.mosymovie.service;


import com.example.mosymovie.entity.Events;
import jakarta.transaction.Transactional;
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
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class EventsService {
    private WebDriver driver;
    private boolean check = false;
    private static String event_url = "https://event.lottecinema.co.kr/NLCHS/Event/DetailList?code=20#none";

    public List<Events> getEventsData() throws IOException{

        List<Events> eventsList = new ArrayList<Events>();

        try{
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\he308\\chromedriver_win32 (1)\\chromedriver.exe");

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
                    Events events = Events.builder()
                            .url(content.findElement(By.tagName("a")).getAttribute("href"))
                            .image(content.findElement(By.cssSelector("a img")).getAttribute("src"))
                            .title(content.findElement(By.cssSelector("a img")).getAttribute("alt"))
                            .period(content.findElement(By.cssSelector("a div.itm_date")).getText())
                            .movieTitle(extractTitle)
                            .build();
                    if (!eventsList.contains(events)) {
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

        return eventsList;
    }
}
