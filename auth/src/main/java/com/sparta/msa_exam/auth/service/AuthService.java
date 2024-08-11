package com.sparta.msa_exam.auth.service;

import com.sparta.msa_exam.auth.dto.UserRequestDto;
import com.sparta.msa_exam.auth.entity.User;
import com.sparta.msa_exam.auth.entity.UserRoleEnum;
import com.sparta.msa_exam.auth.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {
    @Value("${AdminKey}")
    private String admin;

    @Value("${spring.application.name}")
    private String issuer;
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    @Value("${service.jwt.secret-key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    /**
     * 사용자 등록
     * @param userRequestDto 사용자 정보
     * @return 저장된 사용자
     */
    public void signUp(UserRequestDto userRequestDto) {
        String userId = userRequestDto.getUserId();
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        userRequestDto.setPassword(password);
        Optional<User> isExistUserId = userRepository.findByUserId(userId);
        if(isExistUserId.isPresent()){
           throw new IllegalArgumentException("이미 같은 이름의 사용자가 존재함");
        }
        UserRoleEnum role = UserRoleEnum.USER;
        if(userRequestDto.getIsAdmin() != null && !userRequestDto.getIsAdmin().isEmpty()){
            if(admin.equals(userRequestDto.getIsAdmin())){
                role = UserRoleEnum.ADMIN;
            }
        }
        User user = new User(userRequestDto,role);
        userRepository.save(user);
    }

    /**
     * 사용자 인증
     *
     * @param userId 사용자 ID
     * @param password 비밀번호
     * @return JWT 액세스 토큰
     */
    public String signIn(String userId, String password) {
        User user =userRepository.findByUserId(userId).orElseThrow(()->new IllegalArgumentException("Invalid user ID or password"));
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 다름");
        }
        return createToken(user.getUserId(),user.getRole());
    }



    /**
     * 사용자 ID를 받아 JWT 액세스 토큰을 생성합니다.
     *
     * @param role 사용자 ID
     * @return 생성된 JWT 액세스 토큰
     *
     * 토큰 레디스에 저장하고 사용자한테는 이름의 입장권만 발행
     */
    // 토큰 생성
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        Key key = Keys.hmacShaKeyFor(bytes);
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .claim("user_id",username)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }
}