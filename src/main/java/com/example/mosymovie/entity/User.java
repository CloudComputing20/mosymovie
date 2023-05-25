package com.example.mosymovie.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @Column(nullable = false, unique = true)
    private String id;

    @JsonIgnore //데이터 이동에서 민감한 정보를 숨기기
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String emailAddr;

    @Column
    private boolean alarmReceive;

    @Builder
    public User(String password, String name, String emailAddr, String phoneNumber, String id, Boolean alarmReceive){
        this.password = password;
        this.id = id;
        this.name = name;
        this.emailAddr = emailAddr;
        this.phoneNumber = phoneNumber;
        this.alarmReceive = alarmReceive;
    }

    public void UserUpdate(String password, String emailAddr, String phoneNumber, Boolean alarmReceive){
        this.password = password;
        this.emailAddr = emailAddr;
        this.phoneNumber = phoneNumber;
        this.alarmReceive = alarmReceive;
    }
}
