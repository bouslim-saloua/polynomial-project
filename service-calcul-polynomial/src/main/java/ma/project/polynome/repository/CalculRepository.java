package ma.project.polynome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ma.project.polynome.entity.Calcul;

public interface CalculRepository extends JpaRepository<Calcul, Long>{

}
