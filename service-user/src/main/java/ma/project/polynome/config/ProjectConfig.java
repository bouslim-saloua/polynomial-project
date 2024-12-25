package ma.project.polynome.config;

import ma.project.polynome.security.AuthEntryPointJwt;
import ma.project.polynome.security.AuthTokenFilter;
import ma.project.polynome.service.impl.AuthenticationProviderService;
import ma.project.polynome.service.impl.JpaUtilisateurDetailsService;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter{
@Autowired
 private AuthenticationProviderService authenticationProvider;

    
@Autowired
	JpaUtilisateurDetailsService userDetailsService;
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint((AuthenticationEntryPoint) unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers("/api/auth/**").permitAll()
			//.antMatchers("/api/test/**").permitAll()


                .antMatchers("/**").permitAll() //permit all the routers after swagger-ui.html
			
                .anyRequest().authenticated();
                        
		http.addFilterBefore((Filter) authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}