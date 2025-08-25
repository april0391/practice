package edu.jwt.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Verification;
import edu.jwt.model.User;
import edu.jwt.repository.UserRepository;
import edu.jwt.security.PrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }
        String jwt = authorization.replace("Bearer ", "");
        String username = JWT.require(Algorithm.HMAC256(JwtAuthenticationFilter.SECRET))
                .build()
                .verify(jwt)
                .getClaim("username")
                .asString();

        if (username != null) {
            User user = userRepository.findByUsername(username).orElseThrow();
            System.out.println("user = " + user);
            PrincipalDetails principalDetails = new PrincipalDetails(user);
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            Collection<? extends GrantedAuthority> authorities = principalDetails.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                System.out.println("authority = " + authority);
            }
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
