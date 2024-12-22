package ma.project.polynome.security.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data

public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> appRoles=new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<AppRole> getAppRoles() {
        return appRoles;
    }

    public void setAppRoles(Collection<AppRole> appRoles) {
        this.appRoles = appRoles;
    }

    public AppUser(){}
    public AppUser(Long id, String username, String password, Collection<AppRole> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.appRoles = roles;
    }
}
