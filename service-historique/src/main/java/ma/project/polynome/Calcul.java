package ma.project.polynome;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Calcul {
	private Long id;
	private String polynome;
	private String resultatFactorisation;
    private String resultatRacines;
}
