package com.example.mosymovie.service;

import com.example.mosymovie.dto.PreferGenreDto;
import com.example.mosymovie.entity.PreferGenre;
import com.example.mosymovie.entity.User;
import com.example.mosymovie.repository.MemberRepository;
import com.example.mosymovie.repository.PreferGenreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SelectGenreService {
    private final MemberRepository memberRepository;
    private final PreferGenreRepository preferGenreRepository;

    @Transactional
    public String addPreferGenre(PreferGenreDto dto) throws NoSuchAlgorithmException {
        String str = dto.getPreferMovie();
        String changedPreferMovieStr = "";
        List<String> strList = List.of(str.split(","));
        for (int i = 0; i < strList.size(); i++) {
            String middle = strList.get(i);
            String change = "\"" + middle + "\"";
            changedPreferMovieStr += change;

            // 마지막 요소가 아닐 때만 쉼표를 추가
            if (i < strList.size() - 1) {
                changedPreferMovieStr += ",";
            }
        }
                PreferGenre prefer = PreferGenre.builder()
                .userID(dto.getUserID())
                .preferMovie(changedPreferMovieStr)
                .build();
        System.out.println(prefer.getUserID()+", "+prefer.getPreferMovie());
        preferGenreRepository.save(prefer);

        return preferGenreRepository.findAll().toString();
    }
}
