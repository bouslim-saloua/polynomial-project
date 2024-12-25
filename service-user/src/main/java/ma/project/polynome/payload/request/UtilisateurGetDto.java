package ma.project.polynome.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class UtilisateurGetDto {
	private Long id;
	private String nom;
	private String prenom; 
	private String email;

	public UtilisateurGetDto() {
	}

	public UtilisateurGetDto(Long id, String nom, String prenom, String email) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
	}
}
