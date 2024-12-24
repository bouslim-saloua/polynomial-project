package ma.project.polynome;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="SERVICE-CALCUL-POLYNOMIAL")
public interface CalculService {
	@GetMapping(path="/calcule/{id}")
	public Calcul calculById(@PathVariable Long id);
}
