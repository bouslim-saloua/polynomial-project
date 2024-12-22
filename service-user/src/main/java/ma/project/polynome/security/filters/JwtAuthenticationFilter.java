package ma.project.polynome.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.project.polynome.security.SecurityConfig;
import org.bouncycastle.math.ec.rfc8032.Ed448;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private SecurityConfig securityConfig;
    private AuthenticationManager authenticationManager;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, SecurityConfig securityConfig) {
        this.authenticationManager = authenticationManager;
        this.securityConfig = securityConfig;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("attemptAuthentication");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username);
        System.out.println(password);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication");
        User user = (User) authResult.getPrincipal();
        Algorithm algorithm= Algorithm.HMAC256("mysecret1234");
        String jwtAccesToken= JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis()+5*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role",user.getAuthorities().stream().map(ga->ga.getAuthority()).collect(Collectors.toList()))
                .sign(algorithm);
        response.setHeader("Authorization", jwtAccesToken);
    }
}
