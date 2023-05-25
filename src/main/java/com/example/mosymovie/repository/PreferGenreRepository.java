package com.example.mosymovie.repository;

import com.example.mosymovie.entity.PreferGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferGenreRepository extends JpaRepository<PreferGenre, Integer> {
}
