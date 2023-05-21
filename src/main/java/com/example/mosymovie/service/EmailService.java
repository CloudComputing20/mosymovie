package com.example.mosymovie.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage.RecipientType;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService implements MailServiceInter{
    private final JavaMailSender javaMailSender; //Bean 등록해둔 MailConfig를 javaMailSender라는 이름으로 autowired
    public static final String ePw = createKey(); //인증 번호

    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException{
        log.info("보내는 대상: "+ to);
        log.info("인증 번호: "+ ePw);

        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("MosyMovie 회원가입 이메일 인증");

        String msg="";
        msg += "<div style='margin:100px;'>";
        msg += "<h1> 안녕하세요! </h1>";
        msg += "<h1> MosyMovie 회원가입에 필요한 인증번호를 보내드립니다.</h1>";
        msg += "<br>";
        msg += "<p> 아래 제시되는 코드를 인증 번호 입력칸에 입력해주세요.<p>";
        msg += "<br>";
        msg += "<div align='center' style='border:1px solid black; font-family:verdana;'>";
        msg += "<h3 style='color:blue;'> 회원가입 인증 코드입니다.</h3>";
        msg += "<div style='font-size:130%'>";
        msg += "CODE : <strong>";
        msg += ePw + "</strong><div><br/>";
        msg += "</div>";
        message.setText(msg, "utf-8", "html");
        message.setFrom(new InternetAddress("he3080@naver.com", "MosyMovie_Admin"));

        return message;
    }

    //인증코드 생성
    public static String createKey(){
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for(int i = 0;i < 6;i++){
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }

    //메일 발송
    @Override
    public String sendSimpleMessage(String to) throws Exception {

        MimeMessage message = createMessage(to);
        try{
            javaMailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw;
    }
}
