package ma.project.polynome.payload.request;

import java.util.Set;
import javax.validation.constraints.*;

public class SignupRequest {
    @NotBlank
// @Size(min = 3, max = 20)
// private String username;
private String nom;
@NotBlank
private String prenom;

 @NotBlank
 @Size(max = 50)
 @Email
 private String email;

 private Set<String> role;

 @NotBlank
 @Size(min = 6, max = 40)
 private String password;

/* public String getUsername() {
   return username;
 }

 public void setUsername(String username) {
   this.username = username;
 }*/

 public String getEmail() {
   return email;
 }

 public void setEmail(String email) {
   this.email = email;
 }

 public String getPassword() {
   return password;
 }

 public void setPassword(String password) {
   this.password = password;
 }

 public Set<String> getRole() {
   return this.role;
 }

 public void setRole(Set<String> role) {
   this.role = role;
 }

   public String getNom() {
       return nom;
   }

   public void setNom(String nom) {
       this.nom = nom;
   }

   public String getPrenom() {
       return prenom;
   }

   public void setPrenom(String prenom) {
       this.prenom = prenom;
   }

  

}