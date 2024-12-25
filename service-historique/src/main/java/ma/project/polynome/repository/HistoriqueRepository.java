package ma.project.polynome.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.project.polynome.entity.Historique;

@Repository
public interface HistoriqueRepository extends JpaRepository<Historique, Long>{
	List<Historique> findByUtilisateurId(Long utilisateurId);
}
