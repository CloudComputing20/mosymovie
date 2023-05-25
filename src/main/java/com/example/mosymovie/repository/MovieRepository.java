package com.example.mosymovie.repository;

import com.example.mosymovie.entity.Movie;
import com.example.mosymovie.entity.PreferGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
