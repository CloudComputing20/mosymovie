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
        PreferGenre prefer = PreferGenre.builder()
                .userID(dto.getUserID())
                .preferMovie(dto.getPreferMovie())
                .build();

        preferGenreRepository.save(prefer);

        return preferGenreRepository.findAll().toString();
    }
}
