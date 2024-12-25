package ma.project.polynome.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
public class Calcul {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String polynome;
    private String resultatFactorisation;
    private String resultatRacines;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolynome() {
        return polynome;
    }

    public void setPolynome(String polynome) {
        this.polynome = polynome;
    }

    public String getResultatFactorisation() {
        return resultatFactorisation;
    }

    public void setResultatFactorisation(String resultatFactorisation) {
        this.resultatFactorisation = resultatFactorisation;
    }

    public String getResultatRacines() {
        return resultatRacines;
    }

    public void setResultatRacines(String resultatRacines) {
        this.resultatRacines = resultatRacines;
    }
}