package edu.jwt.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MyFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 토큰
        if (request.getMethod().equalsIgnoreCase("post")) {
            System.out.println("포스트 요청 들어옴");
            String headerAuth = request.getHeader("Authorization");
            System.out.println("headerAuth = " + headerAuth);

            if (headerAuth.equals("cos")) {
                filterChain.doFilter(request, response);
            } else {
                response.getWriter().println("인증안됨");
            }
        }
        filterChain.doFilter(request, response);
    }

}
