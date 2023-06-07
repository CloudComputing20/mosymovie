package com.example.mosymovie.repository;

import com.example.mosymovie.entity.Movie;
import com.example.mosymovie.entity.PreferGenre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Movie findByTitle(String title);
}
