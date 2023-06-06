package com.example.mosymovie.controller;

import com.example.mosymovie.dto.UserDto;
import com.example.mosymovie.service.EmailService;
import com.example.mosymovie.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AccountController {
    private final EmailService emailService;
    private final MemberService memberService;

    @GetMapping("signup")
    public String signUp(){
        return "signup";
    }

    @PostMapping("signup/mailConfirm") //이메일 인증하기
    @ResponseBody
    public String mailConfirm(@RequestParam String email) throws Exception{
        String code = emailService.sendSimpleMessage(email);
        log.info("인증코드: "+ code);
        return code;
    }

    @PostMapping("verifyCode")
    @ResponseBody
    public boolean verifyCode(@RequestParam String code){
        if(emailService.ePw.equals(code)){
            return true;
        }else{
            return false;
        }
    }

    @PostMapping("signup")
    public String signUpPost(@Valid @RequestBody UserDto dto, BindingResult bindingResult) throws Exception{
        if(bindingResult.hasErrors()){
            return "signup";
        }else{
            if(memberService.joinCheck(dto)){
                return memberService.join(dto)+"님 환영합니다.";
            }else{
                return "이미 존재하는 회원입니다.";
            }
        }
    }

    //id중복 체크
    @PostMapping("idCheck")
    @ResponseBody
    public boolean idCheck(@RequestBody UserDto dto){
        boolean count = memberService.sameIdCheck(dto.getId());
        return count;
    }

    @PostMapping("login")
    public String login(@RequestBody UserDto dto, HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        UserDto findMemberDto = memberService.login(dto);
        if(findMemberDto != null){
            session.setAttribute("message", "ID나 비밀번호가 다릅니다.");
            return "login";
        }
        return "redirect:/"; //메인 화면과 연결할것
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        request.getSession(true);
        return "redirect:/";
    }
}
