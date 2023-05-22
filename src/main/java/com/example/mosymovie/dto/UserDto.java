package com.example.mosymovie.dto;

import com.example.mosymovie.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.*;

//프론트에서 컨트롤러로 데이터가 넘어올 때, User가 아닌 RegisterUserDto로 데이터를 받는다.
//프론트 -> 컨트롤러 -> Dto -> 서비스(Dto를 Entity로 전환) -> Repository
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {

    private int userID;
    @NotNull
    private String id;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String emailAddr;
    private boolean alarmReceive;

    @Builder
    public RegisterUserDto(String id, String password, String name, String phoneNumber, String emailAddr, Boolean alarmReceive){
        this.alarmReceive = alarmReceive;
        this.emailAddr = emailAddr;
        this.id = id;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public RegisterUserDto(User user) {
        this.id = user.getId();
        this.password = user.getPassword();
    }

    public User toEntity(){
        User member = User.builder()
                .name(name)
                .password(password)
                .emailAddr(emailAddr)
                .phoneNumber(phoneNumber)
                .id(id)
                .alarmReceive(alarmReceive)
                .build();
        return member;
    }

    public void passwordEncoding(String encodingPassword){
        this.password = encodingPassword;
    }
}
