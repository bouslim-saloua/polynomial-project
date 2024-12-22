package ma.project.polynome.security;

import ma.project.polynome.security.entities.AppUser;
import ma.project.polynome.security.filters.JwtAuthenticationFilter;
import ma.project.polynome.security.service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class SecurityConfig {

    private final AccountService accountService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(AccountService accountService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.accountService = accountService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            AppUser user = accountService.loadUserByUsername(username);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            user.getAppRoles().forEach(role ->
                    authorities.add(new SimpleGrantedAuthority(role.getRoleName()))
            );
            return new User(user.getUsername(), user.getPassword(), authorities);
        };
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/home").permitAll();
            auth.anyRequest().authenticated();
        });
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}


