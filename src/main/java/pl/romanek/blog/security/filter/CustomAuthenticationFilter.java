package pl.romanek.blog.security.filter;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import pl.romanek.blog.security.SecurityUser;

@Component
public class CustomAuthenticationFilter extends BasicAuthenticationFilter {

        @Value("${jwt.secret}")
        String secret;

        public CustomAuthenticationFilter(@Lazy AuthenticationManager authenticationManager) {
                super(authenticationManager);
        }

        @Override
        protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                        Authentication authentication) throws IOException {
                SecurityUser user = (SecurityUser) authentication.getPrincipal();
                Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
                String accessToken = JWT.create()
                                .withSubject(String.valueOf(user.getId()))
                                .withExpiresAt(new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000))
                                .withIssuer(request.getRequestURL().toString())
                                .withClaim("roles",
                                                user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                                                .collect(Collectors.toList()))
                                .sign(algorithm);

                ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                                .path("/")
                                .sameSite("None")
                                .domain("mikroblogfront.azurewebsites.net")
                                .httpOnly(true)
                                .secure(true)
                                .build();
                response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        }
}
