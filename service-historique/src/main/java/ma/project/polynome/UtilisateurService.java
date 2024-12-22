package ma.project.polynome;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="SERVICE-USER")
public interface UtilisateurService {
	@GetMapping(path="/users/{id}")
	public Utilisateur utilisateurById(@PathVariable Long id);
}
