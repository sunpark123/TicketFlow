package com.sunpark.ticketflow.gateway.JWT;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {
    private final JwtUtil jwtUtil;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (
                path.startsWith("/auth/login") ||
                path.startsWith("/auth/register") ||
                path.startsWith("/verification/request") ||
                path.startsWith("/verification/verify") ||
                path.startsWith("/token/get/access")
            )
        {
            return chain.filter(exchange);
        }


        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtUtil.validateAccessToken(token)) {
                String username = jwtUtil.extractAccessUserId(token);

                // 1. SecurityContext에 인증 정보 등록 (비동기 방식)
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.emptyList());

                // 2. 헤더 변조 및 SecurityContext 적용
                return chain.filter(exchange.mutate()
                                .request(r -> r.header("X-User-Id", username))
                                .build())
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
            }
        }

        return chain.filter(exchange);
    }
}