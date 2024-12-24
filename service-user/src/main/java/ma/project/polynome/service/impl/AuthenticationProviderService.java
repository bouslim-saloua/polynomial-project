package ma.project.polynome.service.impl;

import lombok.RequiredArgsConstructor;
import ma.project.polynome.entity.CustomUtilisateurDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationProviderService implements AuthenticationProvider{
   @Autowired 
 private JpaUtilisateurDetailsService utilisateurDetailsService;

@Override
public Authentication authenticate(Authentication authentication) throws AuthenticationException{
BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
String username = authentication.getName();
String password = authentication.getCredentials().toString();

    CustomUtilisateurDetails utilisateur = utilisateurDetailsService.loadUserByUsername(username);
    
            
            return checkPassword(utilisateur, password, bCryptPasswordEncoder);
      
//throw new BadCredentialsException("Bad credentials");
}

@Override
public boolean supports(Class<?> aClass){
return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
}

private Authentication checkPassword(CustomUtilisateurDetails user, String rawPassword, PasswordEncoder encoder){
if(encoder.matches(rawPassword, user.getPassword())){
return new UsernamePasswordAuthenticationToken(
user.getUsername(),
user.getPassword(),
user.getAuthorities()
);
}else{
throw new BadCredentialsException("Bad credentials");
}
}

}