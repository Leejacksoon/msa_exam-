package com.sparta.msa_exam.gateway.filter;

import com.sparta.msa_exam.gateway.jwt.JwtUtil;
import com.sparta.msa_exam.gateway.security.JwtAuthorization;
import com.sparta.msa_exam.gateway.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtTokenFilter implements WebFilter {
    private final JwtUtil jwtUtil;
    private final JwtAuthorization jwtAuthorization;

    public JwtTokenFilter(JwtUtil jwtUtil, UserDetailsServiceImpl jwtAuthorization) {
        this.jwtUtil = jwtUtil;
        this.jwtAuthorization = new JwtAuthorization(jwtAuthorization);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) { // 인증해야하는 놈들만 걸렸으면 좋겠는데
        String token = jwtUtil.resolveToken(exchange.getRequest());
        if (token != null && jwtUtil.validateToken(token) && !exchange.getRequest().getHeaders().containsKey("Auth")) {
            Claims userInfo = jwtUtil.getUserInfoFromToken(token);
            SecurityContext auth = jwtAuthorization.setAuthentication(userInfo.getSubject());
            exchange.getRequest().mutate()
                    .header("Auth", "true")
                    .header("X-User-Id", userInfo.get("user_id").toString())
                    .build();
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(auth)));
        }
        return chain.filter(exchange).then();
    }
}
//                               인가처리하고 인가처리됬으면  	        여기서 엔드포인트보냄
//ObservationWebFilterChain -> DefaultServerWebExchange-> SecurityContextServerWebExchangeWebFilter
