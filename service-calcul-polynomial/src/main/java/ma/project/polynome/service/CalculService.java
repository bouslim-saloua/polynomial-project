package ma.project.polynome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ma.project.polynome.entity.Calcul;
import ma.project.polynome.repository.CalculRepository;

import java.util.ArrayList;
import java.util.Map;

@Service
public class CalculService {

    private final String BASE_CALCULATION_URL = "http://127.0.0.1:5000"; // URL du microservice Python

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CalculRepository calculRepository;

    // Méthode pour la factorisation
    public Map<String, Object> factoriser(String polynome) {
        // Appeler la méthode pour factoriser et sauvegarder les résultats
        Map<String, Object> result = envoyerRequeteAuMicroservice(polynome, "/factorisation");
        String resultatFactorisation = (String) result.get("factorisation");
        Long id = sauvegarderCalcul(polynome, resultatFactorisation, null);

        // Ajouter l'ID à la réponse
        result.put("id", id);

        return result;
    }

    // Méthode pour le calcul des racines
    public Map<String, Object> calculerRacines(String polynome) {
        // Appeler le microservice pour obtenir les racines
        Map<String, Object> result = envoyerRequeteAuMicroservice(polynome, "/racines");

        // Vérifier si 'racines' est une liste et la convertir en chaîne
        Object racinesObj = result.get("racines");
        String racinesStr = "";
        if (racinesObj instanceof ArrayList) {
            // Si c'est une ArrayList, convertissez-la en chaîne
            ArrayList<?> racinesList = (ArrayList<?>) racinesObj;
            racinesStr = String.join(", ", racinesList.stream()
                                                      .map(Object::toString)
                                                      .toArray(String[]::new));
        } else if (racinesObj instanceof String) {
            // Si c'est une chaîne, l'utiliser directement
            racinesStr = (String) racinesObj;
        }

        // Sauvegarder les calculs dans la base de données
        Long id = sauvegarderCalcul(polynome, null, racinesStr);

        // Ajouter l'ID à la réponse
        result.put("id", id);

        return result;
    }



    // Méthode générique pour envoyer une requête au microservice Python
    private Map<String, Object> envoyerRequeteAuMicroservice(String polynome, String endpoint) {
        try {
            // Créer l'objet JSON à envoyer
            String json = "{ \"polynome\": \"" + polynome + "\" }";

            // Créer l'entité HTTP avec le corps et les en-têtes
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<String> entity = new HttpEntity<>(json, headers);

            // Envoyer la requête POST et récupérer la réponse
            ResponseEntity<Map> response = restTemplate.exchange(
                    BASE_CALCULATION_URL + endpoint,
                    HttpMethod.POST,
                    entity,
                    Map.class);

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul : " + e.getMessage());
        }
    }

    // Méthode pour sauvegarder le calcul dans la base de données
    private Long sauvegarderCalcul(String polynome, String resultatFactorisation, String resultatRacines) {
        // Créer un objet Calcul pour stocker les informations
        Calcul calcul = new Calcul();
        calcul.setPolynome(polynome);
        calcul.setResultatFactorisation(resultatFactorisation);
        calcul.setResultatRacines(resultatRacines);
        return calculRepository.save(calcul).getId();
        }
}
