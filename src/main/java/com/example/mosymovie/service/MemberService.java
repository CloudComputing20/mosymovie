package com.example.mosymovie.service;

import com.example.mosymovie.dto.RegisterUserDto;
import com.example.mosymovie.entity.User;
import com.example.mosymovie.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public boolean joinCheck(RegisterUserDto dto){
        User findMember = memberRepository.findByUserId(dto.getId());
        if(findMember == null){
            return true;
        }else{
            return false;
        }
    }

    public boolean sameIdCheck(String id){
        User findMember = memberRepository.findByUserId(id);
        if(findMember == null){
            return false;
        }else{
            return true;
        }
    }

    //dto의 비밀번호를 암호화 해서 dto를 entity로 만들어 주고, repository를 이용해 db에 회원 save
    @Transactional
    public String join(RegisterUserDto dto) throws NoSuchAlgorithmException{
        dto.passwordEncoding(encrypt(dto.getPassword()));
        User member = dto.toEntity();
        User findMember = memberRepository.findByUserId(member.getId());
        if(findMember == null){
            return member.getId();
        }else{
            return null;
        }
    }

    public static String encrypt(String password) throws  NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] passByte = password.getBytes(StandardCharsets.UTF_8);
        md.reset();
        byte[] digested = md.digest(passByte);
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < digested.length;i++){
            sb.append(Integer.toHexString(0xff & digested[i]));
        }
        return sb.toString();
    }

    public RegisterUserDto login(RegisterUserDto dto) throws Exception{
        User findMember = memberRepository.findByUserId(dto.getId());
        dto.passwordEncoding(encrypt(dto.getPassword()));

        if(findMember != null){
            RegisterUserDto findMemberDto = new RegisterUserDto(findMember);
            if(dto.getPassword().equals(findMemberDto.getPassword())){
                return findMemberDto;
            }
        }
        return null;
    }
}
