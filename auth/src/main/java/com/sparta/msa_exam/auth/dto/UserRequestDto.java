package com.sparta.msa_exam.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    String UserId;
    String password;
    String isAdmin;
}
