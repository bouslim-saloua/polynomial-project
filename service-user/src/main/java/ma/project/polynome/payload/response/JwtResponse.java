package ma.project.polynome.payload.response;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
      private String token;
  private String type = "Bearer";
  private Long id;
  private String email;
  private List<String> roles;
private String nom;
private String prenom;


  public JwtResponse(String accessToken, Long id, String email, String nom, String prenom, List<String> roles) {
    this.token = accessToken;
    this.id = id;
   
    this.email = email;
    this.roles = roles;
this.nom = nom;
this.prenom  = prenom;
  }

    /*public JwtResponse(String jwt, Long id, String username, List<String> roles) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }*/
}