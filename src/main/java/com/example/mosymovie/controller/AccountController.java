package com.example.mosymovie.controller;

import com.example.mosymovie.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AccountController {
    private final EmailService emailService;

    @PostMapping("signup/mailConfirm")
    @ResponseBody
    public String mailConfirm(@RequestParam String email) throws Exception{
        String code = emailService.sendSimpleMessage(email);
        log.info("인증코드: "+ code);
        return code;
    }
}
