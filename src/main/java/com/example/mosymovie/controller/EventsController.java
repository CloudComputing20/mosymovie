package com.example.mosymovie.controller;

import com.example.mosymovie.entity.Events;
import com.example.mosymovie.service.EventsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class EventsController {
    private final EventsService eventsService;

    @GetMapping("events")
    public List<Events> getAllEvents(){
        return eventsService.getAllEvents();
    }
}
