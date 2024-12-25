package ma.project.polynome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ma.project.polynome.Calcul;
import ma.project.polynome.CalculService;
import ma.project.polynome.Utilisateur;
import ma.project.polynome.UtilisateurService;
import ma.project.polynome.entity.Historique;
import ma.project.polynome.repository.HistoriqueRepository;

@RestController
public class HistoriqueController {
	@Autowired
	HistoriqueRepository historiqueRepository;
	
	@Autowired
	UtilisateurService utilisateurService;
	
	@Autowired
	CalculService calculService;
	
	@GetMapping("/historiques")
	public ResponseEntity<List<Historique>> findAll() {
		try {
			List<Historique> historiques = historiqueRepository.findAll();
			return ResponseEntity.ok().body(historiques);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/historiques/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		try {
			Historique historique = historiqueRepository.findById(id)
					.orElseThrow(()-> new Exception("Historique Introuvable"));
			historique.setUtilisateur(utilisateurService.utilisateurById(historique.getUtilisateurId()));
			historique.setCalcul(calculService.calculById(historique.getCalculId()));
			return ResponseEntity.ok(historique);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Historique not found with ID : " + id);
		}
	}
	
	@GetMapping("/historiques/utilisateur/{id}")
	public ResponseEntity<List<Historique>> findByUtilisateur(@PathVariable Long id) {
		try {
			Utilisateur utilisateur = utilisateurService.utilisateurById(id);
			if(utilisateur != null) {
				List<Historique> historiques = historiqueRepository.findByUtilisateurId(id);
				return ResponseEntity.ok(historiques);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/historiques/{utilisateurId}/{calculId}")
	public ResponseEntity<Object> save(@PathVariable Long utilisateurId, @PathVariable Long calculId, @RequestBody Historique historique) {
		try {
			Utilisateur utilisateur = utilisateurService.utilisateurById(utilisateurId);
			Calcul calcul = calculService.calculById(calculId);
			if(utilisateur != null ) {
				historique.setUtilisateur(utilisateur);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Utilisateur Not found with ID: " + utilisateurId);
			}
			
			if(calcul != null) {
				historique.setCalcul(calcul);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Calcul Not found with ID: " + calculId);
			}
			
			historique.setUtilisateurId(utilisateurId);
			historique.setCalculId(calculId);
			Historique savedHistorique = historiqueRepository.save(historique);
			return ResponseEntity.ok(savedHistorique);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error saving Historique : " + e.getMessage());
		}
	}
	
	@PutMapping("/historiques/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Historique updatedHistorique) {
		try {
			Historique existingHistorique = historiqueRepository.findById(id)
					.orElseThrow(() -> new Exception("Historique not found with ID: " + id));
			if (updatedHistorique.getDateCreation() != null && !updatedHistorique.getDateCreation().toString().isEmpty()) {
	            existingHistorique.setDateCreation(updatedHistorique.getDateCreation());
	        }
			
			//save the updated Historique
			Historique savedHistorique = historiqueRepository.save(existingHistorique);
			return ResponseEntity.ok(savedHistorique);
		} catch (Exception e) {
			  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			            .body("Error updating historique: " + e.getMessage());
		}
	}
	
	@DeleteMapping("/historiques/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable Long id) {
	    try {
	        Historique existingHistorique = historiqueRepository.findById(id)
	                .orElseThrow(() -> new Exception("Historique not found with ID: " + id));
	        
	        historiqueRepository.delete(existingHistorique);
	        return ResponseEntity.ok("Historique with ID " + id + " has been deleted successfully.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error deleting historique: " + e.getMessage());
	    }
	}

}
