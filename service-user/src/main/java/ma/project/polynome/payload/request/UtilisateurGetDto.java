package ma.project.polynome.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurGetDto {
	private Long id;
	private String nom;
	private String prenom; 
	private String email;
}
