package ma.project.polynome;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur {
	private Long id;
	private String nom;
	private String prenom;
	private String email;
	private String password;
}
