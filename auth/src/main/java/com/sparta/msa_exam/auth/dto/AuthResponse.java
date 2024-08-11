package com.sparta.msa_exam.auth.dto;

import lombok.Getter;

@Getter
public class AuthResponse {
    String token;
    public AuthResponse(String token) {
        this.token = token;
    }
}
