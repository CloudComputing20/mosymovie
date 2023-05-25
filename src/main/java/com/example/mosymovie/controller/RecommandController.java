package com.example.mosymovie.controller;

import com.example.mosymovie.service.RecommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RecommandController {

    private final RecommandService recommandService;
}
