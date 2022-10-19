package pl.romanek.blog.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            token = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("accessToken"))
                    .findFirst().orElse(null).getValue();
        }
        if (token != null) {
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String username = decodedJWT.getSubject();
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
            List<GrantedAuthority> authorities = Arrays.stream(roles).map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null,
                    authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
