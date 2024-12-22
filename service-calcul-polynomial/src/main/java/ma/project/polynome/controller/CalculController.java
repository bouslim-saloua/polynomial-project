package ma.project.polynome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ma.project.polynome.service.CalculService;

import java.util.Map;

@RestController
@RequestMapping("calcule")
public class CalculController {

    @Autowired
    private CalculService calculService;

    // Endpoint pour factoriser le polynôme
    @PostMapping("/factorisation")
    public Map<String, Object> factoriser(@RequestBody Map<String, String> requestBody) {
        String polynome = requestBody.get("polynome");
        return calculService.factoriser(polynome);  // Appel au service pour factoriser et sauvegarder
    }

    // Endpoint pour calculer les racines du polynôme
    @PostMapping("/racines")
    public Map<String, Object> calculerRacines(@RequestBody Map<String, String> requestBody) {
        String polynome = requestBody.get("polynome");
        return calculService.calculerRacines(polynome);  // Appel au service pour calculer les racines et sauvegarder
    }
}
