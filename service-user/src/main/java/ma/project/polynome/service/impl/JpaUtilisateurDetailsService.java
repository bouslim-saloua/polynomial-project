package ma.project.polynome.service.impl;

import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import ma.project.polynome.entity.CustomUtilisateurDetails;
import ma.project.polynome.entity.Utilisateur;
import ma.project.polynome.repository.UtilisateurRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@RequiredArgsConstructor
@Service
public class JpaUtilisateurDetailsService implements UserDetailsService{

	final UtilisateurRepository utilisateurRepository;

	public JpaUtilisateurDetailsService(UtilisateurRepository utilisateurRepository) {
		this.utilisateurRepository = utilisateurRepository;
	}

	@Override
	public CustomUtilisateurDetails loadUserByUsername(String username){
		Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("Problem during authentication!");

		Utilisateur utilisateur = utilisateurRepository.findByEmail(username).orElseThrow(s);
		return CustomUtilisateurDetails.build(utilisateur);
	}
}
