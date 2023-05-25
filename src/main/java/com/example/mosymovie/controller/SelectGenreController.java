package com.example.mosymovie.controller;

import com.example.mosymovie.service.SelectGenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SelectGenreController {

    private final SelectGenreService selectGenreService;

    @GetMapping("selectGenre")
    public String selectGenre(){
        return "selectGenre";
    }

    @PostMapping("selectGenre")
    @ResponseBody
    public String PreferGenre(@RequestParam List<String> movieGenreList, String userID) throws NoSuchAlgorithmException {
        selectGenreService.addPreferGenre(movieGenreList, userID);

        return ""; //메인 화면
    }
}
