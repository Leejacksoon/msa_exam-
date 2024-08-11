package com.sparta.msa_exam.auth.entity;

import com.sparta.msa_exam.auth.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(UserRequestDto userRequestDto, UserRoleEnum role){
        this.userId = userRequestDto.getUserId();
        this.password = userRequestDto.getPassword();
        this.role = role;
    }
}
