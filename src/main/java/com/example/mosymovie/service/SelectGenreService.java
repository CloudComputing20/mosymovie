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
    public String addPreferGenre(List<String> movieGenreList, String userID) throws NoSuchAlgorithmException {
        User findUser = memberRepository.findByid(userID);

        for(int i = 0;i < movieGenreList.size();i++){
            PreferGenreDto preferGenreDto = new PreferGenreDto(movieGenreList.get(i), findUser.getUserID());
            PreferGenre preferGenre = preferGenreDto.toEntity();
            preferGenreRepository.save(preferGenre);
        }

        return preferGenreRepository.findAll().toString();
    }
}
