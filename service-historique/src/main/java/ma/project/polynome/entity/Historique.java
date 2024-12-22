package ma.project.polynome.entity;

import java.util.Date;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.project.polynome.Calcul;
import ma.project.polynome.Utilisateur;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Historique {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.DATE)
	private Date dateCreation;
	@Nullable
	private Long utilisateurId;
	@Nullable
	private Long calculId;
	
	@Transient
	@ManyToOne
	private Utilisateur utilisateur;
	
	@Transient
	@ManyToOne
	private Calcul calcul;
}
