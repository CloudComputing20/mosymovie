package com.example.mosymovie.dto;

import com.example.mosymovie.entity.Events;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventsDto {

    private int eventID;
    @NotNull
    private String url;
    @NotNull
    private String period;
    @NotNull
    private String title;

    @NotNull
    private String movieTitle;
    private String image;

    @Builder
    public EventsDto(String url, String title, String period, String image, String movieTitle){
        this.image = image;
        this.url = url;
        this.title = title;
        this.period = period;
        this.movieTitle = movieTitle;
    }

    public Events toEntity(){
        Events event = Events.builder()
                .period(period)
                .url(url)
                .image(image)
                .title(title)
                .movieTitle(movieTitle)
                .build();
        return event;
    }
}
