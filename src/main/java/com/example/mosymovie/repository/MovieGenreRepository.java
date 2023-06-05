package com.example.mosymovie.repository;

import com.example.mosymovie.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieGenreRepository extends JpaRepository<Genre, Integer> {
}
