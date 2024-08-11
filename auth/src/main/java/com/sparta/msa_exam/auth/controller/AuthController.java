package com.sparta.msa_exam.auth.controller;

import com.sparta.msa_exam.auth.dto.AuthResponse;
import com.sparta.msa_exam.auth.dto.LoginRequestDto;
import com.sparta.msa_exam.auth.dto.UserRequestDto;
import com.sparta.msa_exam.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthController {
    @Value("${server.port}")
    private String port;

    private final AuthService authService;

    @PostMapping("/auth/signIn") //로그인 로그인할때 토큰 전달
    public ResponseEntity<AuthResponse> SignIn(@RequestBody LoginRequestDto userRequestDto){
        String token = authService.signIn(userRequestDto.getUserId(), userRequestDto.getPassword());
        return ResponseEntity.ok()
                .header("Server-Port",port)
                .body(new AuthResponse(token));
    }
    @PostMapping("/auth/signUp") // 회원가입
    public ResponseEntity<?> signUp(@RequestBody UserRequestDto userRequestDto) {
        System.out.println("?");
        authService.signUp(userRequestDto);
        return ResponseEntity.ok()
                .header("Server-Port",port)
                .body("회원가입완료");
    }
}




