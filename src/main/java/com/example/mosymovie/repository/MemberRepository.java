package com.example.mosymovie.repository;

import com.example.mosymovie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<User, String>{
    public User findByUserId(String id);
}
