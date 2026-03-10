package com.sunpark.ticketflow.Filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GatewayFilter implements Filter {

    @Value("${gateway.secret}")
    private String gatewaySecret;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        String secret = request.getHeader("X-Gateway-Secret");

        if (!gatewaySecret.equals(secret)) {
            ((HttpServletResponse) res).sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }
        chain.doFilter(req, res);
    }
}
